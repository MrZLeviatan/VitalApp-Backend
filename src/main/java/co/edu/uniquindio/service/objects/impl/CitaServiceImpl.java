package co.edu.uniquindio.service.objects.impl;

import co.edu.uniquindio.dto.cita.CambiarEstadoCitaDto;
import co.edu.uniquindio.dto.cita.CitaDto;
import co.edu.uniquindio.dto.cita.RegistrarCitaDto;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.mapper.objects.CitaMapper;
import co.edu.uniquindio.models.enums.EstadoCita;
import co.edu.uniquindio.models.enums.TipoAlerta;
import co.edu.uniquindio.models.objects.Agenda;
import co.edu.uniquindio.models.objects.Cita;
import co.edu.uniquindio.models.objects.Formula;
import co.edu.uniquindio.repository.objects.AgendaRepo;
import co.edu.uniquindio.repository.objects.CitaRepo;
import co.edu.uniquindio.service.objects.AgendaService;
import co.edu.uniquindio.service.objects.AlertaService;
import co.edu.uniquindio.service.objects.CitasService;
import co.edu.uniquindio.service.users.MedicoService;
import co.edu.uniquindio.service.users.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CitaServiceImpl implements CitasService {


    private final CitaRepo citaRepo;
    private final CitaMapper citaMapper;
    private final AgendaRepo agendaRepo;
    private final AgendaService agendaService;
    private final PacienteService pacienteService;
    private final MedicoService medicoService;
    private final AlertaService alertaService;


    @Override
    public void registraCita(RegistrarCitaDto registrarCitaDto) throws ElementoNoEncontradoException {

        // 1. Convertir la Cita mediante el mapeo
        Cita cita = citaMapper.toEntity(registrarCitaDto);
        
        // 2. se busca el paciente y médico asociado a la cita
        cita.setPaciente(pacienteService.buscarPacienteId(registrarCitaDto.idPaciente()));

        cita.setMedico(medicoService.buscarMedicoId(registrarCitaDto.idMedico()));

        Agenda agenda = agendaService.obtenerAgendaId(registrarCitaDto.idAgenda());

        cita.setAgenda(agenda);

        // 3. Se guarda la cita
        citaRepo.save(cita);

        // 4. Se cambia el estado de la agenda asociada a la cita
        agenda.setCita(cita);
        agenda.setActivo(false);

        agendaRepo.save(agenda);

        alertaService.crearAlerta(cita.getPaciente(), TipoAlerta.REGISTRO_CITA,
                "Tu cita fue registrada correctamente para el día " + cita.getAgenda().getDia() + " a las " + cita.getAgenda().getHoraInicio());

    }


    @Override
    public void cancelarCita(CambiarEstadoCitaDto cambiarEstadoCitaDto)
            throws ElementoNoEncontradoException {

        Cita cita = obtenerCita(cambiarEstadoCitaDto.idCita());

        // Paciente Cancela la cita
        cita.setEstadoCita(EstadoCita.CANCELADA);

        // Se verifica si la cita fue cancelada una semana antes para liberar esa agenda
        Agenda agenda = cita.getAgenda();

        if (agenda != null) {
            // Calcular la diferencia en días entre hoy y la fecha de la agenda
            long diasHastaAgenda = ChronoUnit.DAYS.between(LocalDate.now(), agenda.getDia());

            // Si falta al menos 7 días, liberar la agenda
            if (diasHastaAgenda >= 7) {
                agenda.setActivo(true); // marca como liberada/inactiva
                agenda.setCita(null);    // desasocia la cita cancelada
                agendaRepo.save(agenda);
            }
        }
        // Se guardan los cambios a la cita
        citaRepo.save(cita);
    }


    @Override
    public void cambiarEstadoCita(CambiarEstadoCitaDto cambiarEstadoCitaDto) throws ElementoNoEncontradoException {

        Cita cita = obtenerCita(cambiarEstadoCitaDto.idCita());
        // Cambia el estado de la cita EN_REVISION cuando el médico comience con el proceso
        cita.setEstadoCita(EstadoCita.EN_REVISION);
        citaRepo.save(cita);

        alertaService.crearAlerta(cita.getPaciente(), TipoAlerta.CANCELACION,
                "Tu cita del " + cita.getAgenda().getDia() + " fue cancelada.");
    }


    @Override
    public CitaDto obtenerCitaDtoId(Long id) throws ElementoNoEncontradoException {
        return citaMapper.toDto(obtenerCita(id));
    }


    @Override
    public Cita obtenerCita(Long id) throws ElementoNoEncontradoException {
        return citaRepo.findById(id)
                .orElseThrow(()-> new ElementoNoEncontradoException("Cita no registrada en el sistema."));
    }


    @Override
    public List<CitaDto> obtenerCitasPaciente(Long idPaciente)
            throws ElementoNoEncontradoException {
        return pacienteService.buscarPacienteId(idPaciente).getCitas()
                .stream()
                .map(citaMapper :: toDto)
                .toList();
    }


    @Override
    public List<CitaDto> obtenerCitasPacientePendientes(Long idPaciente) throws ElementoNoEncontradoException {
        return pacienteService.buscarPacienteId(idPaciente).getCitas()
                .stream()
                .filter(cita -> cita.getEstadoCita() == EstadoCita.AGENDADA)
                .map(citaMapper::toDto)
                .toList();
    }


    @Override
    public List<CitaDto> obtenerCitasMedico(Long idMedico)
            throws ElementoNoEncontradoException{
        return medicoService.buscarMedicoId(idMedico).getCitas()
                .stream()
                .map(citaMapper :: toDto)
                .toList();
    }


    @Override
    public void agregarFormulaCita(Cita cita, Formula formula) {
        cita.setFormula(formula);
        cita.setEstadoCita(EstadoCita.COMPLETADA);
        citaRepo.save(cita);
    }


}
