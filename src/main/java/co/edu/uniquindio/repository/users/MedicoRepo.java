package co.edu.uniquindio.repository.users;

import co.edu.uniquindio.models.users.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicoRepo extends JpaRepository<Medico,Long>, JpaSpecificationExecutor<Medico> {


    Optional<Medico> findById(Long id);

    Optional<Medico> findByUser_Email(String email);

    List<Medico> findAllByEspecialidad_Id(Long id);

    boolean existsByUser_Email(String nombre);

}
