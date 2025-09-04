package mx.gob.sev.serv.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Bitacora")
public class Bitacora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "IdUsuarios", nullable = false)
    private Integer idUsuarios;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdAcciones", nullable = false)
    private Accion accion;

    @Column(nullable = false)
    private Integer activo = 1;

    @Column(name = "FechaCreacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "FechaModificacion")
    private LocalDateTime fechaModificacion;
}