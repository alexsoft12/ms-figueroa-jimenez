package dev.alexsoft.msfigueroajimenez.application.Controller;

import dev.alexsoft.msfigueroajimenez.domain.aggregates.dto.EmpresaDto;
import dev.alexsoft.msfigueroajimenez.domain.aggregates.request.EmpresaRequest;
import dev.alexsoft.msfigueroajimenez.domain.ports.in.EmpresaServiceIn;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/empresas")
public class EmpresaController {
    private final EmpresaServiceIn empresaServiceIn;

    @PostMapping
    public ResponseEntity<EmpresaDto> register(@RequestBody EmpresaRequest empresaRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(empresaServiceIn.save(empresaRequest));
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDto> getById(@PathVariable Long id) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(empresaServiceIn.getById(id).get());
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
    public ResponseEntity<List<EmpresaDto>> getAll () {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(empresaServiceIn.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaDto> update(@PathVariable Long id, @RequestBody EmpresaRequest empresaRequest) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(empresaServiceIn.update(id, empresaRequest));
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
            empresaServiceIn.delete(id);
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
