package dev.alexsoft.msfigueroajimenez.infrastructure.mapper;

import dev.alexsoft.msfigueroajimenez.domain.aggregates.dto.EmpresaDto;
import dev.alexsoft.msfigueroajimenez.infrastructure.entity.EmpresaEntity;

import java.util.List;

public class EmpresaMapper {

    public static EmpresaDto fromEntityToDto(EmpresaEntity empresaEntity) {
        EmpresaDto empresaDto = new EmpresaDto();
        empresaDto.setId(empresaEntity.getId());
        empresaDto.setRazonSocial(empresaEntity.getRazonSocial());
        empresaDto.setTipoDocumento(empresaEntity.getTipoDocumento());
        empresaDto.setNumeroDocumento(empresaEntity.getNumeroDocumento());
        empresaDto.setEstado(empresaEntity.getEstado());
        empresaDto.setCondicion(empresaEntity.getCondicion());
        empresaDto.setDireccion(empresaEntity.getDireccion());
        empresaDto.setDistrito(empresaEntity.getDistrito());
        empresaDto.setProvincia(empresaEntity.getProvincia());
        empresaDto.setDepartamento(empresaEntity.getDepartamento());
        empresaDto.setEsAgenteRetencion(empresaEntity.getEsAgenteRetencion());
        empresaDto.setUsuaCrea(empresaEntity.getUsuaCrea());
        empresaDto.setDateCreate(empresaEntity.getDateCreate());
        empresaDto.setUsuaModif(empresaEntity.getUsuaModif());
        empresaDto.setDateModif(empresaEntity.getDateModif());
        return empresaDto;
    }

    public static List<EmpresaDto> fromEntitiesToDtoList(List<EmpresaEntity> empresaEntities) {
        return empresaEntities.stream().map(EmpresaMapper::fromEntityToDto).toList();
    }
}
