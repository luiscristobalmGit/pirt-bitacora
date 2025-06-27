package mx.gob.sev.serv.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BitacoraResponse {
    private Integer id;
    private Integer idUsuarios;
    private Integer idAcciones;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private String descripcionAccion;
}