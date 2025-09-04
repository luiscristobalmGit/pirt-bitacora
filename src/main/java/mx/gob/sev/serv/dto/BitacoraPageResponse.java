package mx.gob.sev.serv.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class BitacoraPageResponse {
    private List<BitacoraDetalleResponse> bitacoras;
    private int totalRegistros;
    private int paginaActual;
    private int registrosPorPagina;
}