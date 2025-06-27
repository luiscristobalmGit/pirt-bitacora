package mx.gob.sev.serv.service;

import mx.gob.sev.serv.dto.BitacoraRequest;
import mx.gob.sev.serv.dto.BitacoraResponse;
import mx.gob.sev.serv.exception.NotFoundException;
import mx.gob.sev.serv.model.Accion;
import mx.gob.sev.serv.model.Bitacora;
import mx.gob.sev.serv.repository.AccionRepository;
import mx.gob.sev.serv.repository.BitacoraRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BitacoraServiceImpl implements BitacoraService {

    private final BitacoraRepository bitacoraRepository;
    private final AccionRepository accionRepository;
    private final ModelMapper modelMapper;

    public BitacoraServiceImpl(BitacoraRepository bitacoraRepository, 
                             AccionRepository accionRepository,
                             ModelMapper modelMapper) {
        this.bitacoraRepository = bitacoraRepository;
        this.accionRepository = accionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public BitacoraResponse save(BitacoraRequest request) {
        Accion accion = accionRepository.findById(request.getIdAcciones())
                .orElseThrow(() -> new NotFoundException("Acción no encontrada con id: " + request.getIdAcciones()));
        
        Bitacora bitacora = modelMapper.map(request, Bitacora.class);
        bitacora.setAccion(accion);
        bitacora.setActivo(request.getActivo() != null ? request.getActivo() : true);
        bitacora = bitacoraRepository.save(bitacora);
        
        return convertToDto(bitacora);
    }

    @Override
    public List<BitacoraResponse> findAll() {
        return bitacoraRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BitacoraResponse findById(Integer id) {
        Bitacora bitacora = bitacoraRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Bitácora no encontrada con id: " + id));
        return convertToDto(bitacora);
    }

    private BitacoraResponse convertToDto(Bitacora bitacora) {
        BitacoraResponse response = modelMapper.map(bitacora, BitacoraResponse.class);
        response.setIdAcciones(bitacora.getAccion().getId());
        response.setDescripcionAccion(bitacora.getAccion().getDescripcion());
        return response;
    }
}