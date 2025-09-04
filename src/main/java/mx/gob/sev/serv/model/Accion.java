package mx.gob.sev.serv.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Acciones", schema = "dbo")
public class Accion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT") // Especificamos el tipo de columna
    private Integer id; 

    @Column(nullable = false)
    private String accion;

    @Column(nullable = false)
    private Integer activo = 1;

    @Column(name = "FechaCreacion", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "FechaModificacion")
    private LocalDateTime fechaModificacion;

    private String descripcion;
}