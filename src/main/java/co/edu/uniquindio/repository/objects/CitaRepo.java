package co.edu.uniquindio.repository.objects;

import co.edu.uniquindio.models.objects.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitaRepo extends JpaRepository<Cita,Long>, JpaSpecificationExecutor<Cita> {


    List<Cita> findAllByPaciente_Id(Long id);

    List<Cita> findAllByMedico_Id(Long id);
}
