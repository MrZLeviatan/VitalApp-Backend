package co.edu.uniquindio.repository;

import co.edu.uniquindio.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Paciente findByDocumento(String documento);
}