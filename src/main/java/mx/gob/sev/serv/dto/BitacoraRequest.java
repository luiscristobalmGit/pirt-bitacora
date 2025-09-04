package mx.gob.sev.serv.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class BitacoraRequest {
    @NotNull(message = "El ID de usuario es requerido")
    private Integer idUsuarios;
    
    @NotNull(message = "La cuenta es requerida")
    @Size(max = 50, message = "La cuenta no puede exceder los 50 caracteres")
    private String cuenta;
    
    @NotNull(message = "El ID de acción es requerido")
    private Integer idAcciones;
    
    private Integer activo;
}