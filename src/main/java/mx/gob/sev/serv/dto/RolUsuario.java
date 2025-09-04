package mx.gob.sev.serv.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolUsuario {
    private Integer idTipoRol;
    private String tipoRol;
}