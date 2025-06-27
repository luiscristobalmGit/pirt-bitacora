package mx.gob.sev.serv.controller;

import mx.gob.sev.serv.dto.BitacoraRequest;
import mx.gob.sev.serv.dto.BitacoraResponse;
import mx.gob.sev.serv.service.BitacoraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bitacora")
public class BitacoraController {

    private final BitacoraService bitacoraService;

    public BitacoraController(BitacoraService bitacoraService) {
        this.bitacoraService = bitacoraService;
    }

    @GetMapping
    public ResponseEntity<List<BitacoraResponse>> getAllBitacoras() {
        return ResponseEntity.ok(bitacoraService.findAll());
    }

    @PostMapping
    public ResponseEntity<BitacoraResponse> createBitacora(@RequestBody BitacoraRequest request) {
        return ResponseEntity.ok(bitacoraService.save(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BitacoraResponse> getBitacoraById(@PathVariable Integer id) {
        return ResponseEntity.ok(bitacoraService.findById(id));
    }
}