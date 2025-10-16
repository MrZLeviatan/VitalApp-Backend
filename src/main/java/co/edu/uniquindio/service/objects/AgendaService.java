package co.edu.uniquindio.service.objects;

import co.edu.uniquindio.dto.agenda.AgendaDto;
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

}
