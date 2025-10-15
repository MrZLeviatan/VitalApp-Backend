package co.edu.uniquindio.repository;

import co.edu.uniquindio.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    List<Medico> findByEspecializacion(String especializacion);
}