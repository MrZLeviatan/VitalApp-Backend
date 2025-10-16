package co.edu.uniquindio.config;

import co.edu.uniquindio.models.users.Medico;
import co.edu.uniquindio.repository.users.MedicoRepo;
import co.edu.uniquindio.service.objects.AgendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling // Activa el soporte para ejecutar tareas de forma automática
@RequiredArgsConstructor
public class SchedulerConfig {

    private final MedicoRepo medicoRepo;
    private final AgendaService agendaService;


    // Tarea programada: Generar las agendas de todos los médicos cada primer día de la semana.
    @Scheduled(cron = "0 0 0 ? * MON") // Se ejecuta todos los lunes a las 00:00
    public void generarAgendaMensual() {
        List<Medico> medicos = medicoRepo.findAll();
        for (Medico medico : medicos) {
            agendaService.generarAgendaMensual(medico);
        }
    }

}
