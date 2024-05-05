package dev.alexsoft.msfigueroajimenez.domain.ports.in;

import dev.alexsoft.msfigueroajimenez.domain.aggregates.dto.PersonaDto;
import dev.alexsoft.msfigueroajimenez.domain.aggregates.request.PersonaRequest;

import java.util.List;
import java.util.Optional;

public interface PersonaServiceIn {
    PersonaDto save(PersonaRequest personaRequest);
    Optional<PersonaDto> getById(Long id);
    List<PersonaDto> getAll();
    PersonaDto update(Long id, PersonaRequest personaRequest);
    void delete(Long id);
}
