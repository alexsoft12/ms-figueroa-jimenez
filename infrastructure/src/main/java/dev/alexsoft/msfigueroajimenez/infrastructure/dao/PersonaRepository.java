package dev.alexsoft.msfigueroajimenez.infrastructure.dao;

import dev.alexsoft.msfigueroajimenez.infrastructure.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<PersonaEntity, Long> {
}
