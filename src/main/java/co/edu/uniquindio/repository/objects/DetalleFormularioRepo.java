package co.edu.uniquindio.repository.objects;

import co.edu.uniquindio.models.objects.DetalleFormula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleFormularioRepo extends JpaRepository<DetalleFormula,Long>, JpaSpecificationExecutor<DetalleFormula> {


    List<DetalleFormula> findAllByFormula_Id(Long formulaId);



}
