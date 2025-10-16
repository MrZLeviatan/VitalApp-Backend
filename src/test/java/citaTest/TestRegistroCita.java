package citaTest;

import co.edu.uniquindio.dto.cita.RegistrarCitaDto;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.mapper.objects.CitaMapper;
import co.edu.uniquindio.models.objects.Agenda;
import co.edu.uniquindio.models.objects.Cita;
import co.edu.uniquindio.models.users.Medico;
import co.edu.uniquindio.models.users.Paciente;
import co.edu.uniquindio.repository.objects.AgendaRepo;
import co.edu.uniquindio.repository.objects.CitaRepo;
import co.edu.uniquindio.service.objects.AgendaService;
import co.edu.uniquindio.service.objects.impl.CitaServiceImpl;
import co.edu.uniquindio.service.users.MedicoService;
import co.edu.uniquindio.service.users.PacienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Pruebas unitarias para el método registrarCita del servicio CitaService
public class TestRegistroCita {


    @Mock
    private CitaMapper citaMapper;

    @Mock
    private PacienteService pacienteService;

    @Mock
    private MedicoService medicoService;

    @Mock
    private AgendaService agendaService;

    @Mock
    private CitaRepo citaRepo;

    @Mock
    private AgendaRepo agendaRepo;

    @InjectMocks
    private CitaServiceImpl citasService;

    private RegistrarCitaDto citaValidaDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        citaValidaDto = new RegistrarCitaDto(
                "Dolor de cabeza fuerte",
                1L, // idPaciente
                2L, // idMedico
                3L  // idAgenda
        );
    }

    // Registro de cita correctamente
    @Test
    void registrarCita_Correcto() throws ElementoNoEncontradoException {
        // Mockear entidades
        Paciente paciente = new Paciente();
        Medico medico = new Medico();
        Agenda agenda = new Agenda();
        Cita cita = new Cita();

        // Configurar mocks
        when(citaMapper.toEntity(any())).thenReturn(cita);
        when(pacienteService.buscarPacienteId(1L)).thenReturn(paciente);
        when(medicoService.buscarMedicoId(2L)).thenReturn(medico);
        when(agendaService.obtenerAgendaId(3L)).thenReturn(agenda);

        // Ejecutar método
        citasService.registraCita(citaValidaDto);

        // Verificar llamadas correctas
        verify(pacienteService).buscarPacienteId(1L);
        verify(medicoService).buscarMedicoId(2L);
        verify(agendaService).obtenerAgendaId(3L);
        verify(citaRepo).save(cita);
        verify(agendaRepo).save(agenda);

        // Verificar cambios en la agenda
        assertFalse(agenda.isActivo(), "La agenda debe quedar inactiva después de asignar la cita.");
        assertEquals(cita, agenda.getCita(), "La cita debe quedar enlazada a la agenda.");
    }


    // Registro cita incorrecto paciente no existe
    @Test
    void registrarCita_PacienteNoExiste_LanzaExcepcion() throws ElementoNoEncontradoException {
        when(citaMapper.toEntity(any())).thenReturn(new Cita());
        when(pacienteService.buscarPacienteId(1L))
                .thenThrow(new ElementoNoEncontradoException("Paciente no encontrado"));

        ElementoNoEncontradoException ex = assertThrows(
                ElementoNoEncontradoException.class,
                () -> citasService.registraCita(citaValidaDto)
        );

        assertEquals("Paciente no encontrado", ex.getMessage());

        // Verificar que no se guardó nada
        verify(citaRepo, never()).save(any());
        verify(agendaRepo, never()).save(any());
    }


    // Registro cita incorrecto agenda no existe
    @Test
    void registrarCita_AgendaNoExiste_LanzaExcepcion() throws ElementoNoEncontradoException {
        when(citaMapper.toEntity(any())).thenReturn(new Cita());
        when(pacienteService.buscarPacienteId(1L)).thenReturn(new Paciente());
        when(medicoService.buscarMedicoId(2L)).thenReturn(new Medico());
        when(agendaService.obtenerAgendaId(3L))
                .thenThrow(new ElementoNoEncontradoException("Agenda no encontrada"));

        ElementoNoEncontradoException ex = assertThrows(
                ElementoNoEncontradoException.class,
                () -> citasService.registraCita(citaValidaDto)
        );

        assertEquals("Agenda no encontrada", ex.getMessage());

        verify(citaRepo, never()).save(any());
        verify(agendaRepo, never()).save(any());
    }
}
