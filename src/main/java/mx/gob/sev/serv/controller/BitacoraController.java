package mx.gob.sev.serv.controller;

import mx.gob.sev.serv.dto.*;
import mx.gob.sev.serv.service.BitacoraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/bitacora")
@Validated
public class BitacoraController {

    private final BitacoraService bitacoraService;

    public BitacoraController(BitacoraService bitacoraService) {
        this.bitacoraService = bitacoraService;
    }

    @PostMapping
    public ResponseEntity<BitacoraResponse> crearEntradaBitacora(@Valid @RequestBody BitacoraRequest request) {
        BitacoraResponse response = bitacoraService.create(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<BitacoraResponse> actualizarEntradaBitacora(@Valid @RequestBody BitacoraUpdateRequest request) {
        BitacoraResponse response = bitacoraService.update(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BitacoraResponse> eliminarEntradaBitacora(@PathVariable Integer id) {
        BitacoraResponse response = bitacoraService.delete(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BitacoraResponse> obtenerEntradaPorId(@PathVariable Integer id) {
        BitacoraResponse response = bitacoraService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<BitacoraDetalleResponse>> obtenerTodasLasEntradas() {
        List<BitacoraDetalleResponse> entradas = bitacoraService.getAll();
        return ResponseEntity.ok(entradas);
    }

    @GetMapping("/filtros")
    public ResponseEntity<BitacoraPageResponse> obtenerEntradasConFiltros(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) Integer idUsuarios,
            @RequestParam(required = false) Integer idAcciones,
            @RequestParam(required = false) Integer activo,
            @RequestParam(defaultValue = "1") int pagina,
            @RequestParam(defaultValue = "10") int registrosPorPagina) {
        
        BitacoraPageResponse response = bitacoraService.getWithFilters(
            id, idUsuarios, idAcciones, activo, pagina, registrosPorPagina);
        return ResponseEntity.ok(response);
    }
}