package co.edu.uniquindio.repository.objects;

import co.edu.uniquindio.models.objects.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface AlertaRepo extends JpaRepository<Alerta,Long>, JpaSpecificationExecutor<Alerta> {


    List<Alerta> findByPacienteId(Long id);

}
