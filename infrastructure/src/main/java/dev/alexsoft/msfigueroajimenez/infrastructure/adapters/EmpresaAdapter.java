package dev.alexsoft.msfigueroajimenez.infrastructure.adapters;

import dev.alexsoft.msfigueroajimenez.domain.aggregates.constants.Constant;
import dev.alexsoft.msfigueroajimenez.domain.aggregates.dto.EmpresaDto;
import dev.alexsoft.msfigueroajimenez.domain.aggregates.dto.SunatDto;
import dev.alexsoft.msfigueroajimenez.domain.aggregates.request.EmpresaRequest;
import dev.alexsoft.msfigueroajimenez.domain.ports.out.EmpresaServiceOut;
import dev.alexsoft.msfigueroajimenez.infrastructure.client.ApisNetSunatClient;
import dev.alexsoft.msfigueroajimenez.infrastructure.dao.EmpresaRepository;
import dev.alexsoft.msfigueroajimenez.infrastructure.entity.EmpresaEntity;
import dev.alexsoft.msfigueroajimenez.infrastructure.mapper.EmpresaMapper;
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
public class EmpresaAdapter implements EmpresaServiceOut {

    private final EmpresaRepository empresaRepository;
    private final ApisNetSunatClient clientSunat;
    private final RedisService redisService;

    @Value("${token.apis_net}")
    private String token;

    @Override
    public EmpresaDto save(EmpresaRequest empresaRequest) {
        EmpresaEntity empresaEntity = getEmpresaEntity(empresaRequest, null);
        return EmpresaMapper.fromEntityToDto(empresaRepository.save(empresaEntity));
    }

    private EmpresaEntity getEmpresaEntity(EmpresaRequest empresaRequest, EmpresaEntity empresa) {
        SunatDto sunatDto = getExecSunat(empresaRequest.getNumeroDocumento());
        EmpresaEntity empresaEntity;
        empresaEntity = Objects.requireNonNullElseGet(empresa, EmpresaEntity::new);
        empresaEntity.setRazonSocial(sunatDto.getRazonSocial());
        empresaEntity.setTipoDocumento(sunatDto.getTipoDocumento());
        empresaEntity.setNumeroDocumento(sunatDto.getNumeroDocumento());
        empresaEntity.setEstado(Constant.STATUS_ACTIVE);
        empresaEntity.setCondicion(sunatDto.getCondicion());
        empresaEntity.setDireccion(sunatDto.getDireccion());
        empresaEntity.setDistrito(sunatDto.getDistrito());
        empresaEntity.setProvincia(sunatDto.getProvincia());
        empresaEntity.setDepartamento(sunatDto.getDepartamento());
        empresaEntity.setEsAgenteRetencion(sunatDto.getEsAgenteRetencion() != null && sunatDto.getEsAgenteRetencion());

        if (empresa != null) {
            empresaEntity.setId(empresa.getId());
            empresaEntity.setUsuaModif(Constant.USU_ADMIN);
            empresaEntity.setDateModif(getTimestamp());
        } else {
            empresaEntity.setUsuaCrea(Constant.USU_ADMIN);
            empresaEntity.setDateCreate(getTimestamp());
        }
        return empresaEntity;
    }

    private SunatDto getExecSunat(String numeroDocumento) {
        String authorization = "Bearer " + token;
        return clientSunat.getSunatInfo(numeroDocumento, authorization);
    }

    private Timestamp getTimestamp(){
        long currenTIme = System.currentTimeMillis();
        return new Timestamp(currenTIme);
    }

    @Override
    public Optional<EmpresaDto> getById(Long id) {
        String redisInfo = redisService.getFromRedis(Constant.REDIS_KEY_OBTENER_EMPRESA + id);
        if(redisInfo != null) {
            EmpresaDto empresaDto = Util.convertirDesdeString(redisInfo, EmpresaDto.class);
            return Optional.of(empresaDto);
        }
            EmpresaEntity empresaEntity = empresaRepository.findById(id).orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
            EmpresaDto empresaDto = EmpresaMapper.fromEntityToDto(empresaEntity);
            String dataParaRedis = Util.convertirAString(empresaDto);
            redisService.saveInRedis(Constant.REDIS_KEY_OBTENER_EMPRESA + id, dataParaRedis, 2);
            return Optional.of(empresaDto);
    }

    @Override
    public List<EmpresaDto> getAll() {
        List<EmpresaEntity> empresaEntities = empresaRepository.findAll();
        return EmpresaMapper.fromEntitiesToDtoList(empresaEntities);
    }

    @Override
    public EmpresaDto update(Long id, EmpresaRequest empresaRequest) {
        Optional<EmpresaEntity> empresa = empresaRepository.findById(id);
        if (empresa.isPresent()) {
            EmpresaEntity empresaEntity = getEmpresaEntity(empresaRequest, empresa.get());
            return EmpresaMapper.fromEntityToDto(empresaRepository.save(empresaEntity));
        } else {
            throw new RuntimeException("Empresa no encontrada");
        }
    }

    @Override
    public void delete(Long id) {
        EmpresaEntity empresa = empresaRepository.findById(id).orElseThrow(()-> new RuntimeException("Empresa no encontrada"));
        empresa.setEstado(Constant.STATUS_INACTIVE);
        empresa.setUsuaModif(Constant.USU_ADMIN);
        empresa.setDateModif(getTimestamp());
        empresaRepository.save(empresa);
    }
}
