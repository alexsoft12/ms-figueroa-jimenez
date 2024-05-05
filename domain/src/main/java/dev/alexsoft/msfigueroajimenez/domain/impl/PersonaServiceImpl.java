package dev.alexsoft.msfigueroajimenez.domain.impl;

import dev.alexsoft.msfigueroajimenez.domain.aggregates.dto.PersonaDto;
import dev.alexsoft.msfigueroajimenez.domain.aggregates.request.PersonaRequest;
import dev.alexsoft.msfigueroajimenez.domain.ports.in.PersonaServiceIn;
import dev.alexsoft.msfigueroajimenez.domain.ports.out.PersonaServiceOut;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonaServiceImpl implements PersonaServiceIn {

    private final PersonaServiceOut personaServiceOut;

    @Override
    public PersonaDto save(PersonaRequest personaRequest) {
       return personaServiceOut.save(personaRequest);
    }

    @Override
    public Optional<PersonaDto> getById(Long id) {
        return personaServiceOut.getById(id);
    }

    @Override
    public List<PersonaDto> getAll() {
        return personaServiceOut.getAll();
    }

    @Override
    public PersonaDto update(Long id, PersonaRequest personaRequest) {
        return personaServiceOut.update(id, personaRequest);
    }

    @Override
    public void delete(Long id) {
        personaServiceOut.delete(id);
    }
}
