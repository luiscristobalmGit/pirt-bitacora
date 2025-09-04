package mx.gob.sev.serv.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class BitacoraResponse {
    private Integer id;
    private Integer idUsuarios;
    private String cuenta;
    private Integer idAcciones;
    private Integer activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private boolean exito;
    private String mensaje;
}