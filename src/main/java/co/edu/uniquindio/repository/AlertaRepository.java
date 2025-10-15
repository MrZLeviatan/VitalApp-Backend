package co.edu.uniquindio.repository;

import co.edu.uniquindio.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    List<Alerta> findByPacienteId(Long pacienteId);
    List<Alerta> findByIsLeidaFalse();
}