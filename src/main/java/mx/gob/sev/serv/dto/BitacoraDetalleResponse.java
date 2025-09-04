package mx.gob.sev.serv.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class BitacoraDetalleResponse {
    private Integer id;
    private Integer idUsuarios;
    private String cuenta;
    private Integer idAcciones;
    private String nombreAccion;
    private Integer activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private String usuarioNombre;
    private String usuarioPaterno;
    private String usuarioMaterno;
    private List<RolUsuario> rolesUsuario; // Cambiado de campos individuales a lista
    private List<CorreoUsuario> correosUsuario; // Cambiado de campos individuales a lista
    private String correoPrincipal;
}