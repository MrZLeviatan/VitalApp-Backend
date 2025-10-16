package co.edu.uniquindio.repository.objects;

import co.edu.uniquindio.models.objects.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface AgendaRepo extends JpaRepository<Agenda,Long>, JpaSpecificationExecutor<Agenda> {


    List<Agenda> findByMedico_Id(Long medicoId);

    Optional<Agenda> findByCita_Id(Long citaId);




}
