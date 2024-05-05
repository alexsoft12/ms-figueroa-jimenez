package dev.alexsoft.msfigueroajimenez.infrastructure.dao;

import dev.alexsoft.msfigueroajimenez.infrastructure.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository  extends JpaRepository<EmpresaEntity, Long> {
}
