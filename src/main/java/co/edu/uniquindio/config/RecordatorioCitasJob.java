package co.edu.uniquindio.config;

import co.edu.uniquindio.models.enums.TipoAlerta;
import co.edu.uniquindio.models.objects.Cita;
import co.edu.uniquindio.repository.objects.CitaRepo;
import co.edu.uniquindio.service.objects.AlertaService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RecordatorioCitasJob {


    private final CitaRepo citaRepo;
    private final AlertaService alertaService;


    // Cada hora revisa si hay citas próximas
    @Scheduled(fixedRate = 3600000)
    public void enviarRecordatorios() {
        LocalDateTime ahora = LocalDateTime.now();

        for (Cita cita : citaRepo.findAll()) {
            LocalDateTime fechaCita = cita.getAgenda().getDia().atTime(cita.getAgenda().getHoraInicio());

            // Faltan 24 horas
            if (fechaCita.minusDays(1).isBefore(ahora) && fechaCita.isAfter(ahora)) {
                alertaService.crearAlerta(cita.getPaciente(), TipoAlerta.RECORDATORIO_DIA,
                        "Recuerda tu cita mañana a las " + cita.getAgenda().getHoraInicio());
            }

            // Faltan 60 minutos
            if (fechaCita.minusHours(1).isBefore(ahora) && fechaCita.isAfter(ahora)) {
                alertaService.crearAlerta(cita.getPaciente(), TipoAlerta.RECORDATORIO_HORA,
                        "Tu cita será en una hora (" + cita.getAgenda().getHoraInicio() + ")");
            }
        }
    }

}
