package mx.gob.sev.serv.repository;

import mx.gob.sev.serv.model.Accion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccionRepository extends JpaRepository<Accion, Integer> {
}