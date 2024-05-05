package dev.alexsoft.msfigueroajimenez.infrastructure.dao;

import dev.alexsoft.msfigueroajimenez.infrastructure.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpresaRepository  extends JpaRepository<EmpresaEntity, Long> {
    Optional<EmpresaEntity> findByNumeroDocumento(String numeroDocumento);
}
