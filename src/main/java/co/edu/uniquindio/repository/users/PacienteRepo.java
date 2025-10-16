package co.edu.uniquindio.repository.users;

import co.edu.uniquindio.models.users.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepo extends JpaRepository<Paciente,Long>, JpaSpecificationExecutor<Paciente> {


    Optional<Paciente> findById(Long id);

    Optional<Paciente> findByUser_Email(String email);

    boolean existsByUser_Email(String nombre);



}
