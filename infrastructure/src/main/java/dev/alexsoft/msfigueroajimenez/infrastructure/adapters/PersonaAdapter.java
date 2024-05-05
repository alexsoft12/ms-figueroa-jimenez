package dev.alexsoft.msfigueroajimenez.infrastructure.adapters;

import dev.alexsoft.msfigueroajimenez.domain.aggregates.constants.Constant;
import dev.alexsoft.msfigueroajimenez.domain.aggregates.dto.PersonaDto;
import dev.alexsoft.msfigueroajimenez.domain.aggregates.dto.ReniecDto;
import dev.alexsoft.msfigueroajimenez.domain.aggregates.request.PersonaRequest;
import dev.alexsoft.msfigueroajimenez.domain.ports.out.PersonaServiceOut;
import dev.alexsoft.msfigueroajimenez.infrastructure.client.ApisNetReniecClient;
import dev.alexsoft.msfigueroajimenez.infrastructure.dao.EmpresaRepository;
import dev.alexsoft.msfigueroajimenez.infrastructure.dao.PersonaRepository;
import dev.alexsoft.msfigueroajimenez.infrastructure.entity.EmpresaEntity;
import dev.alexsoft.msfigueroajimenez.infrastructure.entity.PersonaEntity;
import dev.alexsoft.msfigueroajimenez.infrastructure.mapper.PersonaMapper;
import dev.alexsoft.msfigueroajimenez.infrastructure.redis.RedisService;
import dev.alexsoft.msfigueroajimenez.infrastructure.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonaAdapter implements PersonaServiceOut {

    private final EmpresaRepository empresaRepository;
    private final PersonaRepository personaRepository;
    private final ApisNetReniecClient reniecClient;
    private final RedisService redisService;

    @Value("${token.apis_net}")
    private String token;

    @Override
    public PersonaDto save(PersonaRequest personaRequest) {
        EmpresaEntity empresaEntity = empresaRepository.findByNumeroDocumento(
                personaRequest.getEmpresaRuc()
        ).orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        PersonaEntity personaEntity = getPersonaEntity(personaRequest, null);
        personaEntity.setEmpresa(empresaEntity);
        return PersonaMapper.fromEntityToDto(personaRepository.save(personaEntity));
    }

    private PersonaEntity getPersonaEntity(PersonaRequest personaRequest, PersonaEntity persona) {
        ReniecDto reniecDto = getExecReniec(personaRequest.getNumeroDocumento());
        PersonaEntity personaEntity;
        personaEntity = Objects.requireNonNullElseGet(persona, PersonaEntity::new);
        personaEntity.setNombre(reniecDto.getNombres());
        personaEntity.setApellido(reniecDto.getApellidoPaterno() + " " + reniecDto.getApellidoMaterno());
        personaEntity.setTipoDocumento(reniecDto.getTipoDocumento());
        personaEntity.setNumeroDocumento(reniecDto.getNumeroDocumento());
        personaEntity.setEmail("");
        personaEntity.setTelefono("");
        personaEntity.setDireccion("");
        personaEntity.setEstado(Constant.STATUS_ACTIVE);

        if (persona != null) {
            personaEntity.setId(persona.getId());
            personaEntity.setUsuaModif(Constant.USU_ADMIN);
            personaEntity.setDateModif(getTimestamp());
        } else {
            personaEntity.setUsuaCrea(Constant.USU_ADMIN);
            personaEntity.setDateCreate(getTimestamp());
        }
        return personaEntity;
    }

    private ReniecDto getExecReniec(String numeroDocumento) {
        String authorization = "Bearer " + token;
        return reniecClient.getReniecInfo(numeroDocumento, authorization);

    }

    private Timestamp getTimestamp() {
        long currenTIme = System.currentTimeMillis();
        return new Timestamp(currenTIme);
    }

    @Override
    public Optional<PersonaDto> getById(Long id) {
        String redisInfo = redisService.getFromRedis(Constant.REDIS_KEY_OBTENER_PERSONA + id);
        if (redisInfo != null) {
            PersonaDto personaDto = Util.convertirDesdeString(redisInfo, PersonaDto.class);
            return Optional.ofNullable(personaDto);
        }
        PersonaEntity personaEntity = personaRepository.findById(id).orElseThrow(() -> new RuntimeException("Persona no encontrada"));
        PersonaDto personaDto = PersonaMapper.fromEntityToDto(personaEntity);
        String dataParaRedis = Util.convertirAString(personaDto);
        redisService.saveInRedis(Constant.REDIS_KEY_OBTENER_PERSONA + id, dataParaRedis, Constant.REDIS_EXPIRATION);
        return Optional.of(personaDto);
    }

    @Override
    public List<PersonaDto> getAll() {
        List<PersonaEntity> personaEntities = personaRepository.findAll();
        return PersonaMapper.fromEntitiesToDtoList(personaEntities);
    }

    @Override
    public PersonaDto update(Long id, PersonaRequest personaRequest) {
        EmpresaEntity empresaEntity = empresaRepository.findByNumeroDocumento(
                personaRequest.getEmpresaRuc()
        ).orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
        PersonaEntity personaEntity = personaRepository.findById(id).orElseThrow(() -> new RuntimeException("Persona no encontrada"));
        PersonaEntity personaEntityUpdated = getPersonaEntity(personaRequest, personaEntity);
        personaEntityUpdated.setEmpresa(empresaEntity);
        return PersonaMapper.fromEntityToDto(personaRepository.save(personaEntityUpdated));
    }

    @Override
    public void delete(Long id) {
        PersonaEntity personaEntity = personaRepository.findById(id).orElseThrow(() -> new RuntimeException("Persona no encontrada"));
        personaEntity.setEstado(Constant.STATUS_INACTIVE);
        personaEntity.setUsuaDelet(Constant.USU_ADMIN);
        personaEntity.setDateDelet(getTimestamp());
        String redisInfo = redisService.getFromRedis(Constant.REDIS_KEY_OBTENER_PERSONA + id);
        if (redisInfo != null) {
            redisService.deleteKey(Constant.REDIS_KEY_OBTENER_PERSONA + id);
        }
        personaRepository.save(personaEntity);
    }
}
