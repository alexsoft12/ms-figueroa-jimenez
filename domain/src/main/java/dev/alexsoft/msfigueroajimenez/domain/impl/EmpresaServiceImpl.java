package dev.alexsoft.msfigueroajimenez.domain.impl;

import dev.alexsoft.msfigueroajimenez.domain.aggregates.dto.EmpresaDto;
import dev.alexsoft.msfigueroajimenez.domain.aggregates.request.EmpresaRequest;
import dev.alexsoft.msfigueroajimenez.domain.ports.in.EmpresaServiceIn;
import dev.alexsoft.msfigueroajimenez.domain.ports.out.EmpresaServiceOut;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmpresaServiceImpl implements EmpresaServiceIn {

    private final EmpresaServiceOut empresaServiceOut;

    @Override
    public EmpresaDto save(EmpresaRequest empresaRequest) {
        return empresaServiceOut.save(empresaRequest);
    }

    @Override
    public Optional<EmpresaDto> getById(Long id) {
        return empresaServiceOut.getById(id);
    }

    @Override
    public List<EmpresaDto> getAll() {
        return empresaServiceOut.getAll();
    }

    @Override
    public EmpresaDto update(Long id, EmpresaRequest empresaRequest) {
        return empresaServiceOut.update(id, empresaRequest);
    }

    @Override
    public void delete(Long id) {
        empresaServiceOut.delete(id);
    }
}
