package co.edu.uniquindio.repository.objects;

import co.edu.uniquindio.models.objects.Formula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormulaRepo extends JpaRepository<Formula,Long>, JpaSpecificationExecutor<Formula> {


    List<Formula> findAllByPaciente_Id(Long pacienteId);


}
