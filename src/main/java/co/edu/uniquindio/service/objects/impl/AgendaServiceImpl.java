package co.edu.uniquindio.service.objects.impl;

import co.edu.uniquindio.dto.agenda.AgendaDto;
import co.edu.uniquindio.dto.agenda.RegistrarAgendaDto;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.mapper.objects.AgendaMapper;
import co.edu.uniquindio.models.objects.Agenda;
import co.edu.uniquindio.models.users.Medico;
import co.edu.uniquindio.repository.objects.AgendaRepo;
import co.edu.uniquindio.repository.users.MedicoRepo;
import co.edu.uniquindio.service.objects.AgendaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class AgendaServiceImpl implements AgendaService {


    private final AgendaRepo agendaRepo;
    private final AgendaMapper agendaMapper;
    private final MedicoRepo medicoRepo;

    // Genera automáticamente la agenda semanal de un médico.
    @Override
    public void generarAgendaMensual(Medico medico) {

        List<Agenda> agendas = new ArrayList<>();
        LocalDate fechaActual = LocalDate.now();
        LocalDate finSemana = fechaActual.plusDays(6); // Fin de la semana

        // Recorremos todos los días de la semana desde la fecha actual hasta el fin de semana
        for (LocalDate fecha = fechaActual; !fecha.isAfter(finSemana); fecha = fecha.plusDays(1)){

            // Saltar los domingos
            if (fecha.getDayOfWeek() == DayOfWeek.SUNDAY){
                continue;
            }

            // Horario de 6:00 AM a 8:00 PM
            LocalTime horaInicio = LocalTime.of(6, 0);
            LocalTime horaFin = LocalTime.of(20, 0);

            // Se genera los intervalos de una hora dentro del horario establecido
            while (horaInicio.isBefore(horaFin)) {

                // Saltar de 12 PM a 2 PM ( hora almuerzo )
                if (horaInicio.equals(LocalTime.of(12, 0))) {
                    horaInicio = LocalTime.of(14, 0);
                    continue;
                }

                // Calculamos la siguiente hora del intervalo
                LocalTime siguienteHora = horaInicio.plusHours(1);

                // Se genera una nueva agenda para el horario actual
                Agenda agenda = new Agenda();
                agenda.setDia(fecha);
                agenda.setHoraInicio(horaInicio);
                agenda.setHoraFin(siguienteHora);
                agenda.setActivo(true);
                agenda.setMedico(medico);

                // Agregamos la agenda a la lista de agendas de la semana
                agendas.add(agenda);
                // Pasamos al siguiente bloque de tiempo
                horaInicio = siguienteHora;
            }
        }

        // Guardar todas las agendas en base de datos
        agendaRepo.saveAll(agendas);
    }



    @Override
    public Agenda obtenerAgendaId(Long idAgenda) throws ElementoNoEncontradoException {
        return agendaRepo.findById(idAgenda)
                .orElseThrow(()-> new ElementoNoEncontradoException("Agenda no registrado en el sistema."));
    }


    @Override
    public List<AgendaDto> listarAgendaLibreMedico(Long idMedico) throws ElementoNoEncontradoException {

        Medico medico = medicoRepo.findById(idMedico)
                .orElseThrow(() -> new ElementoNoEncontradoException("No se encontró el médico con id: " + idMedico));

        return medico.getAgenda()
                .stream()
                .filter(Agenda::isActivo)
                .map(agendaMapper::toDto)
                .toList();
    }


    @Override
    public List<AgendaDto> listarAgendaMedicoId(Long idMedico) throws ElementoNoEncontradoException {

        Medico medico = medicoRepo.findById(idMedico)
                .orElseThrow(() -> new ElementoNoEncontradoException("No se encontró el médico con id: " + idMedico));

        return medico.getAgenda()
                .stream()
                .map(agendaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void registrarAgenda(RegistrarAgendaDto dto) throws ElementoNoEncontradoException {
        
        // 1. Verificar que el médico exista
        Medico medico = medicoRepo.findById(dto.idMedico())
                .orElseThrow(() -> new ElementoNoEncontradoException(
                        "No se encontró el médico con ID: " + dto.idMedico()));
        
        // 2. Crear la nueva agenda
        Agenda agenda = new Agenda();
        agenda.setMedico(medico);
        agenda.setDia(dto.dia());
        agenda.setHoraInicio(dto.horaInicio());
        agenda.setHoraFin(dto.horaFin());
        agenda.setActivo(true);
        agenda.setCita(null); // Sin cita asignada aún (horario disponible)
        
        // 3. Guardar en la base de datos
        agendaRepo.save(agenda);
    }

    @Override
    public void eliminarAgenda(Long idAgenda) throws ElementoNoEncontradoException {
        
        // 1. Buscar la agenda
        Agenda agenda = agendaRepo.findById(idAgenda)
                .orElseThrow(() -> new ElementoNoEncontradoException(
                        "No se encontró la agenda con ID: " + idAgenda));
        
        // 2. Verificar que no tenga cita asignada
        if (agenda.getCita() != null) {
            throw new RuntimeException(
                    "No se puede eliminar una agenda con cita asignada. " +
                    "Primero cancele la cita.");
        }
        
        // 3. Desactivar la agenda (borrado lógico)
        agenda.setActivo(false);
        agendaRepo.save(agenda);
        
        // Alternativa: Borrado físico (descomentar si prefieres esto)
        // agendaRepo.delete(agenda);
    }
}
