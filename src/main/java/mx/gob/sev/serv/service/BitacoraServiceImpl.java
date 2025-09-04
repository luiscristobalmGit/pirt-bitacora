package mx.gob.sev.serv.service;

import mx.gob.sev.serv.dto.*;
import mx.gob.sev.serv.exception.NotFoundException;
import mx.gob.sev.serv.exception.ServiceException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class BitacoraServiceImpl implements BitacoraService {

    private static final Logger logger = LoggerFactory.getLogger(BitacoraServiceImpl.class);
    private final EntityManager entityManager;
    private final ObjectMapper objectMapper;

    public BitacoraServiceImpl(EntityManager entityManager, ObjectMapper objectMapper) {
        this.entityManager = entityManager;
        this.objectMapper = objectMapper;
    }

    @Override
    public BitacoraResponse create(BitacoraRequest request) {
        validateCreateRequest(request);
        
        try {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_Bitacora_Manage")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(5, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(6, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(7, Integer.class, ParameterMode.OUT)
                .registerStoredProcedureParameter(8, String.class, ParameterMode.OUT);

            query.setParameter(1, "CREATE")
                .setParameter(2, null)
                .setParameter(3, request.getIdUsuarios())
                .setParameter(4, request.getCuenta())
                .setParameter(5, request.getIdAcciones())
                .setParameter(6, request.getActivo() != null ? request.getActivo() : 1);

            query.execute();
            
            Integer resultado = (Integer) query.getOutputParameterValue(7);
            String mensaje = (String) query.getOutputParameterValue(8);
            
            if (resultado == null || resultado <= 0) {
                throw new ServiceException(mensaje != null ? mensaje : "Error al crear registro en bitácora");
            }
            
            return BitacoraResponse.builder()
                    .id(resultado)
                    .exito(true)
                    .mensaje("Bitácora creada exitosamente")
                    .build();
            
        } catch (Exception e) {
            logger.error("Error al ejecutar procedimiento almacenado", e);
            throw new ServiceException("Error al crear bitácora: " + e.getMessage(), e);
        }
    }

    @Override
    public BitacoraResponse update(BitacoraUpdateRequest request) {
        validateUpdateRequest(request);
        
        try {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_Bitacora_Manage")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(5, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(6, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(7, Integer.class, ParameterMode.OUT)
                .registerStoredProcedureParameter(8, String.class, ParameterMode.OUT);
            
            query.setParameter(1, "UPDATE")
                .setParameter(2, request.getId())
                .setParameter(3, request.getIdUsuarios())
                .setParameter(4, request.getCuenta())
                .setParameter(5, request.getIdAcciones())
                .setParameter(6, request.getActivo());
            
            query.execute();
            
            Integer resultado = (Integer) query.getOutputParameterValue(7);
            String mensaje = (String) query.getOutputParameterValue(8);
            
            if (resultado == null || resultado <= 0) {
                throw new ServiceException(mensaje != null ? mensaje : "Error al actualizar registro de bitácora");
            }
            
            return BitacoraResponse.builder()
                    .id(request.getId())
                    .exito(true)
                    .mensaje("Bitácora actualizada exitosamente")
                    .build();
            
        } catch (Exception e) {
            logger.error("Error al ejecutar procedimiento almacenado para actualización", e);
            throw new ServiceException("Error al actualizar bitácora: " + e.getMessage(), e);
        }
    }

    @Override
    public BitacoraResponse delete(Integer id) {
        validateId(id);
        
        try {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_Bitacora_Manage")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, Integer.class, ParameterMode.OUT)
                .registerStoredProcedureParameter(4, String.class, ParameterMode.OUT);
            
            query.setParameter(1, "DELETE")
                .setParameter(2, id);
            
            query.execute();
            
            Integer resultado = (Integer) query.getOutputParameterValue(3);
            String mensaje = (String) query.getOutputParameterValue(4);
            
            if (resultado == null || resultado <= 0) {
                throw new ServiceException(mensaje != null ? mensaje : "Error al eliminar registro de bitácora");
            }
            
            return BitacoraResponse.builder()
                    .id(id)
                    .exito(true)
                    .mensaje("Bitácora eliminada exitosamente")
                    .build();
            
        } catch (Exception e) {
            logger.error("Error al ejecutar procedimiento almacenado para eliminación", e);
            throw new ServiceException("Error al eliminar bitácora: " + e.getMessage(), e);
        }
    }

    @Override
    public BitacoraResponse getById(Integer id) {
        validateId(id);
        
        BitacoraDetalleResponse detalle = executeReadQuery(id, null, null, null, 1, 1)
            .stream()
            .findFirst()
            .orElseThrow(() -> new NotFoundException("No se encontró registro con ID: " + id));
        
        return BitacoraResponse.builder()
                .id(detalle.getId())
                .idUsuarios(detalle.getIdUsuarios())
                .cuenta(detalle.getCuenta())
                .idAcciones(detalle.getIdAcciones())
                .activo(detalle.getActivo())
                .fechaCreacion(detalle.getFechaCreacion())
                .exito(true)
                .mensaje("Consulta exitosa")
                .build();
    }

    @Override
    public List<BitacoraDetalleResponse> getAll() {
        List<BitacoraDetalleResponse> resultados = executeReadQuery(null, null, null, null, 1, Integer.MAX_VALUE);
        
        if (resultados.isEmpty()) {
            throw new NotFoundException("No se encontraron registros en la bitácora");
        }
        
        return resultados;
    }

    @Override
    public BitacoraPageResponse getWithFilters(Integer id, Integer idUsuarios, Integer idAcciones,
                                            Integer activo, int pagina, int registrosPorPagina) {
        validatePagination(pagina, registrosPorPagina);
        
        List<BitacoraDetalleResponse> resultados = executeReadQuery(id, idUsuarios, idAcciones, activo, pagina, registrosPorPagina);
        int totalRegistros = getTotalRegistros(id, idUsuarios, idAcciones, activo);
        
        return BitacoraPageResponse.builder()
                .bitacoras(resultados)
                .totalRegistros(totalRegistros)
                .paginaActual(pagina)
                .registrosPorPagina(registrosPorPagina)
                .build();
    }

    private List<BitacoraDetalleResponse> executeReadQuery(Integer id, Integer idUsuarios, 
                                                         Integer idAcciones, Integer activo,
                                                         int pagina, int registrosPorPagina) {
        try {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_Bitacora_Read")
                .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(4, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(5, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(6, Integer.class, ParameterMode.IN);
            
            query.setParameter(1, id)
                .setParameter(2, idUsuarios)
                .setParameter(3, idAcciones)
                .setParameter(4, activo)
                .setParameter(5, pagina)
                .setParameter(6, registrosPorPagina);
            
            return executeAndMapResults(query);
        } catch (Exception e) {
            logger.error("Error al ejecutar consulta de bitácoras", e);
            throw new ServiceException("Error al consultar bitácoras: " + e.getMessage(), e);
        }
    }

    private List<BitacoraDetalleResponse> executeAndMapResults(StoredProcedureQuery query) {
        query.execute();
        
        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();
        Map<Integer, BitacoraDetalleResponse> bitacoraMap = new LinkedHashMap<>();
        
        for (Object[] result : results) {
            try {
                Integer bitacoraId = getSafeInteger(result, 0);
                BitacoraDetalleResponse detalle = bitacoraMap.get(bitacoraId);
                
                if (detalle == null) {
                    String correosJson = result.length > 12 ? getSafeString(result, 12) : null;
                    List<CorreoUsuario> correos = parseCorreosUsuario(correosJson);
                    
                    detalle = BitacoraDetalleResponse.builder()
                        .id(bitacoraId)
                        .idUsuarios(getSafeInteger(result, 1))
                        .cuenta(getSafeString(result, 2))
                        .idAcciones(getSafeInteger(result, 3))
                        .nombreAccion(getSafeString(result, 4))
                        .activo(getSafeInteger(result, 5))
                        .fechaCreacion(getSafeLocalDateTime(result, 6))
                        .fechaModificacion(getSafeLocalDateTime(result, 7))
                        .usuarioNombre(getSafeString(result, 8))
                        .usuarioPaterno(getSafeString(result, 9))
                        .usuarioMaterno(getSafeString(result, 10))
                        .rolesUsuario(new ArrayList<>())
                        .correosUsuario(correos)
                        .correoPrincipal(result.length > 13 ? getSafeString(result, 13) : null)
                        .build();
                    
                    bitacoraMap.put(bitacoraId, detalle);
                }
                
                // Procesar roles
                Integer idTipoRol = getSafeInteger(result, 11);
                String tipoRol = getSafeString(result, 12);
                
                if (idTipoRol != null && tipoRol != null) {
                    RolUsuario rol = RolUsuario.builder()
                        .idTipoRol(idTipoRol)
                        .tipoRol(tipoRol.trim())
                        .build();
                    
                    // Verificar si el rol ya existe para evitar duplicados
                    boolean rolExiste = detalle.getRolesUsuario().stream()
                        .anyMatch(r -> r.getIdTipoRol().equals(idTipoRol));
                    
                    if (!rolExiste) {
                        detalle.getRolesUsuario().add(rol);
                    }
                }
                
            } catch (Exception e) {
                logger.error("Error al mapear fila: {}", e.getMessage());
                throw new ServiceException("Error al procesar datos de bitácora", e);
            }
        }
        
        return new ArrayList<>(bitacoraMap.values());
    }

    private List<CorreoUsuario> parseCorreosUsuario(String correosJson) {
        if (correosJson == null || correosJson.isEmpty() || correosJson.equals("[]") || correosJson.equals("null")) {
            return new ArrayList<>();
        }
        
        try {
            // Limpiar el JSON si viene con escapes
            String jsonClean = correosJson.replace("\\\"", "\"");
            if (jsonClean.startsWith("\"") && jsonClean.endsWith("\"")) {
                jsonClean = jsonClean.substring(1, jsonClean.length() - 1);
            }
            
            return objectMapper.readValue(jsonClean, new TypeReference<List<CorreoUsuario>>() {});
        } catch (Exception e) {
            logger.error("Error al parsear JSON de correos. JSON: {}. Error: {}", correosJson, e.getMessage());
            return new ArrayList<>();
        }
    }

    private int getTotalRegistros(Integer id, Integer idUsuarios, Integer idAcciones, Integer activo) {
        try {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_Bitacora_Read")
                .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(4, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(5, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(6, Integer.class, ParameterMode.IN);
            
            query.setParameter(1, id)
                .setParameter(2, idUsuarios)
                .setParameter(3, idAcciones)
                .setParameter(4, activo)
                .setParameter(5, 1)
                .setParameter(6, Integer.MAX_VALUE);
            
            query.execute();
            
            @SuppressWarnings("unchecked")
            List<Object[]> countResults = query.getResultList();
            return countResults != null ? countResults.size() : 0;
        } catch (Exception e) {
            logger.error("Error al contar registros", e);
            return 0;
        }
    }

    private Integer getSafeInteger(Object[] row, int index) {
        if (row == null || row.length <= index || row[index] == null) {
            return null;
        }
        try {
            if (row[index] instanceof Number) {
                return ((Number) row[index]).intValue();
            }
            return Integer.parseInt(row[index].toString());
        } catch (Exception e) {
            logger.warn("Error al convertir a entero: {}", row[index]);
            return null;
        }
    }

    private String getSafeString(Object[] row, int index) {
        if (row == null || row.length <= index || row[index] == null) {
            return null;
        }
        return row[index].toString();
    }

    private LocalDateTime getSafeLocalDateTime(Object[] row, int index) {
        if (row == null || row.length <= index || row[index] == null) {
            return null;
        }
        try {
            if (row[index] instanceof Timestamp) {
                return ((Timestamp) row[index]).toLocalDateTime();
            }
            return LocalDateTime.parse(row[index].toString().replace(" ", "T"));
        } catch (Exception e) {
            logger.warn("Error al convertir a LocalDateTime: {}", row[index]);
            return null;
        }
    }

    private void validateCreateRequest(BitacoraRequest request) {
        if (request == null) {
            throw new ServiceException("La solicitud no puede ser nula");
        }
        if (request.getIdUsuarios() == null || request.getIdUsuarios() <= 0) {
            throw new ServiceException("El ID de usuario es requerido y debe ser positivo");
        }
        if (request.getCuenta() == null || request.getCuenta().trim().isEmpty()) {
            throw new ServiceException("La cuenta es requerida");
        }
        if (request.getCuenta().length() > 50) {
            throw new ServiceException("La cuenta no puede exceder los 50 caracteres");
        }
        if (request.getIdAcciones() == null || request.getIdAcciones() <= 0) {
            throw new ServiceException("El ID de acción es requerido y debe ser positivo");
        }
        if (request.getActivo() != null && (request.getActivo() < 0 || request.getActivo() > 1)) {
            throw new ServiceException("El valor de activo debe ser 0 o 1");
        }
    }

    private void validateUpdateRequest(BitacoraUpdateRequest request) {
        validateId(request.getId());
        
        if (request.getIdUsuarios() == null || request.getIdUsuarios() <= 0) {
            throw new ServiceException("El ID de usuario es requerido y debe ser positivo");
        }
        if (request.getCuenta() == null || request.getCuenta().trim().isEmpty()) {
            throw new ServiceException("La cuenta es requerida");
        }
        if (request.getCuenta().length() > 50) {
            throw new ServiceException("La cuenta no puede exceder los 50 caracteres");
        }
        if (request.getIdAcciones() == null || request.getIdAcciones() <= 0) {
            throw new ServiceException("El ID de acción es requerido y debe ser positivo");
        }
        if (request.getActivo() == null || (request.getActivo() < 0 || request.getActivo() > 1)) {
            throw new ServiceException("El valor de activo debe ser 0 o 1");
        }
    }

    private void validateId(Integer id) {
        if (id == null || id <= 0) {
            throw new ServiceException("El ID es requerido y debe ser positivo");
        }
    }

    private void validatePagination(int pagina, int registrosPorPagina) {
        if (pagina < 1) {
            throw new ServiceException("El número de página debe ser mayor o igual a 1");
        }
        if (registrosPorPagina < 1 || registrosPorPagina > 100) {
            throw new ServiceException("Los registros por página deben estar entre 1 y 100");
        }
    }
}