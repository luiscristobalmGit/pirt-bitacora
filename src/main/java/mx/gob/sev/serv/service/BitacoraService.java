package mx.gob.sev.serv.service;

import mx.gob.sev.serv.dto.*;
import java.util.List;

public interface BitacoraService {
    BitacoraResponse create(BitacoraRequest request);
    BitacoraResponse update(BitacoraUpdateRequest request);
    BitacoraResponse delete(Integer id);
    BitacoraResponse getById(Integer id);
    List<BitacoraDetalleResponse> getAll();
    BitacoraPageResponse getWithFilters(Integer id, Integer idUsuarios, Integer idAcciones, 
                                      Integer activo, int pagina, int registrosPorPagina);
}