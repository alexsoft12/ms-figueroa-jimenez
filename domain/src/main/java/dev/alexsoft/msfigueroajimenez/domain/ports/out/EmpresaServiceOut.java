package dev.alexsoft.msfigueroajimenez.domain.ports.out;

import dev.alexsoft.msfigueroajimenez.domain.aggregates.dto.EmpresaDto;
import dev.alexsoft.msfigueroajimenez.domain.aggregates.request.EmpresaRequest;

import java.util.List;
import java.util.Optional;

public interface EmpresaServiceOut {
    EmpresaDto save(EmpresaRequest empresaRequest);
    Optional<EmpresaDto> getById(Long id);
    List<EmpresaDto> getAll();
    EmpresaDto update(Long id, EmpresaRequest empresaRequest);
    void delete(Long id);
}
