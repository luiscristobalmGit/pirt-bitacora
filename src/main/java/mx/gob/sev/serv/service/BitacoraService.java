package mx.gob.sev.serv.service;

import mx.gob.sev.serv.dto.BitacoraResponse;
import mx.gob.sev.serv.dto.BitacoraRequest;
import java.util.List;

public interface BitacoraService {
    BitacoraResponse save(BitacoraRequest request);
    List<BitacoraResponse> findAll();
    BitacoraResponse findById(Integer id);
}