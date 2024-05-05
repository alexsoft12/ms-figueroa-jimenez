package dev.alexsoft.msfigueroajimenez.infrastructure.mapper;

import dev.alexsoft.msfigueroajimenez.domain.aggregates.dto.PersonaDto;
import dev.alexsoft.msfigueroajimenez.infrastructure.entity.PersonaEntity;

import java.util.List;

public class PersonaMapper {

    public static PersonaDto fromEntityToDto(PersonaEntity entity) {
        PersonaDto personaDto = new PersonaDto();
        personaDto.setId(entity.getId());
        personaDto.setNombre(entity.getNombre());
        personaDto.setApellido(entity.getApellido());
        personaDto.setTipoDocumento(entity.getTipoDocumento());
        personaDto.setNumeroDocumento(entity.getNumeroDocumento());
        personaDto.setEmail(entity.getEmail());
        personaDto.setTelefono(entity.getTelefono());
        personaDto.setDireccion(entity.getDireccion());
        personaDto.setEstado(entity.getEstado());
        personaDto.setUsuaCrea(entity.getUsuaCrea());
        personaDto.setDateCreate(entity.getDateCreate());
        personaDto.setUsuaModif(entity.getUsuaModif());
        personaDto.setDateModif(entity.getDateModif());
        personaDto.setUsuaDelet(entity.getUsuaDelet());
        personaDto.setDateDelet(entity.getDateDelet());
        personaDto.setEmpresa(EmpresaMapper.fromEntityToDto(entity.getEmpresa()));
        return personaDto;
    }

    public static List<PersonaDto> fromEntitiesToDtoList(List<PersonaEntity> personaEntities) {
        return personaEntities.stream().map(PersonaMapper::fromEntityToDto).toList();
    }
}
