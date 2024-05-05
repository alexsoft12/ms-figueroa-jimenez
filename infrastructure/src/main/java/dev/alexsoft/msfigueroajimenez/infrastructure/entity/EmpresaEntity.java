package dev.alexsoft.msfigueroajimenez.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "empresa_info")
@Getter
@Setter
public class EmpresaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "razonsocial", nullable = false)
    private String razonSocial;
    @Column(name = "tipodocumento", nullable = false, length = 5)
    private String tipoDocumento;
    @Column(name = "numerodocumento", nullable = false, unique = true, length = 20)
    private String numeroDocumento;
    @Column(name = "estado", nullable = false)
    private Integer estado;
    @Column(name = "condicion", nullable = false, length = 50)
    private String condicion;
    @Column(name = "direccion", columnDefinition = "TEXT")
    private String direccion;
    @Column(name = "distrito", length = 100)
    private String distrito;
    @Column(name = "provincia", length = 100)
    private String provincia;
    @Column(name = "departamento", length = 100)
    private String departamento;
    @Column(name = "esagenteretencion", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean esAgenteRetencion = false;
    @Column(name = "usuacrea", nullable = false)
    private String usuaCrea;
    @Column(name = "datecreate", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Timestamp dateCreate;
    @Column(name = "usuamodif")
    private String usuaModif;
    @Column(name = "datemodif", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Timestamp dateModif;
    @Column(name = "usuadelet")
    private String usuaDelet;
    @Column(name = "datedelet", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Timestamp dateDelet;
}
