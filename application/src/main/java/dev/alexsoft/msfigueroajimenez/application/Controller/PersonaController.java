package dev.alexsoft.msfigueroajimenez.application.Controller;

import dev.alexsoft.msfigueroajimenez.domain.aggregates.dto.PersonaDto;
import dev.alexsoft.msfigueroajimenez.domain.aggregates.request.PersonaRequest;
import dev.alexsoft.msfigueroajimenez.domain.ports.in.PersonaServiceIn;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/personas")
public class PersonaController {

    private final PersonaServiceIn personaServiceIn;

    @PostMapping
    public ResponseEntity<PersonaDto> register(@RequestBody PersonaRequest personaRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(personaServiceIn.save(personaRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonaDto> getById(@PathVariable Long id) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(personaServiceIn.getById(id).get());
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<PersonaDto>> getAll () {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(personaServiceIn.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonaDto> update(@PathVariable Long id, @RequestBody PersonaRequest personaRequest){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(personaServiceIn.update(id, personaRequest));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            personaServiceIn.delete(id);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(null);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
