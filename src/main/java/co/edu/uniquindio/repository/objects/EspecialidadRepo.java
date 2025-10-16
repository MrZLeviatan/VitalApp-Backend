package co.edu.uniquindio.repository.objects;

import co.edu.uniquindio.models.tools.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecialidadRepo extends JpaRepository<Especialidad,Long>, JpaSpecificationExecutor<Especialidad> {


}
