package dev.alexsoft.msfigueroajimenez.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "persona")
public class PersonaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "apellido", nullable = false)
    private String apellido;
    @Column(name = "tipodocumento", nullable = false, length = 5)
    private String tipoDocumento;
    @Column(name = "numerodocumento", nullable = false, unique = true, length = 20)
    private String numeroDocumento;
    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;
    @Column(name = "telefono", length = 15)
    private String telefono;
    @Column(name = "direccion", columnDefinition = "TEXT")
    private String direccion;
    @Column(name = "estado", nullable = false)
    private Integer estado;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private EmpresaEntity empresa;
}
