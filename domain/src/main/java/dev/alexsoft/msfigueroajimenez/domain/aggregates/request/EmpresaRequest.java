package dev.alexsoft.msfigueroajimenez.domain.aggregates.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaRequest {
    private String tipoDocumento;
    private String numeroDocumento;
}
