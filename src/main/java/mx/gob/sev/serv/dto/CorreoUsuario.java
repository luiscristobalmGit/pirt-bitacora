package mx.gob.sev.serv.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CorreoUsuario {
    private String correo;
    private String tipo;
    private Boolean correoActivo;
}