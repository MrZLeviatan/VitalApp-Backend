package co.edu.uniquindio.repository.objects;

import co.edu.uniquindio.models.objects.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicamentoRepo extends JpaRepository<Medicamento, Long>, JpaSpecificationExecutor<Medicamento> {



}
