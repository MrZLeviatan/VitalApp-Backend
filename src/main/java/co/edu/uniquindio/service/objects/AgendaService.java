package co.edu.uniquindio.service.objects;

import co.edu.uniquindio.dto.agenda.AgendaDto;
import co.edu.uniquindio.dto.agenda.RegistrarAgendaDto;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.models.objects.Agenda;
import co.edu.uniquindio.models.users.Medico;

import java.util.List;

public interface AgendaService {

    // Genera una agenda mensual al médico
    void generarAgendaMensual(Medico medico);

    // Obtener la Agenda sin el Dto mediante el ID
    Agenda obtenerAgendaId(Long idAgenda)
            throws ElementoNoEncontradoException;

    List<AgendaDto> listarAgendaLibreMedico(Long idMedico)
            throws ElementoNoEncontradoException;

    List<AgendaDto> listarAgendaMedicoId(Long idMedico)
            throws ElementoNoEncontradoException;

    /**
     * Registra un nuevo horario en la agenda de un médico
     * @param dto DTO con los datos del horario a crear
     * @throws ElementoNoEncontradoException si el médico no existe
     */
    void registrarAgenda(RegistrarAgendaDto dto) throws ElementoNoEncontradoException;

    /**
     * Elimina (desactiva) un horario de la agenda
     * @param idAgenda ID del horario a eliminar
     * @throws ElementoNoEncontradoException si la agenda no existe
     */
    void eliminarAgenda(Long idAgenda) throws ElementoNoEncontradoException;

}
