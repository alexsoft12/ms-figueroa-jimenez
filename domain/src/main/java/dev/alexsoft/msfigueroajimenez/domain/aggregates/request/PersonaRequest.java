package dev.alexsoft.msfigueroajimenez.domain.aggregates.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonaRequest {
    private String tipoDocumento;
    private String numeroDocumento;
    private String empresaRuc;
}
