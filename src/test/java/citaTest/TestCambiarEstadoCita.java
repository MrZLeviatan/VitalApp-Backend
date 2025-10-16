package citaTest;

import co.edu.uniquindio.dto.cita.CambiarEstadoCitaDto;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.models.enums.EstadoCita;
import co.edu.uniquindio.models.objects.Agenda;
import co.edu.uniquindio.models.objects.Cita;
import co.edu.uniquindio.models.users.Paciente;
import co.edu.uniquindio.repository.objects.CitaRepo;
import co.edu.uniquindio.service.objects.AlertaService;
import co.edu.uniquindio.service.objects.impl.CitaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Pruebas unitarias del método cambiarEstadoCita del servicio CitasServiceImpl
public class TestCambiarEstadoCita {

    @Mock
    private CitaRepo citaRepo;

    @Mock
    private AlertaService alertaService;

    @InjectMocks
    private CitaServiceImpl citasService;


    private Cita cita;
    private CambiarEstadoCitaDto dtoCorrecto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cita = new Cita();
        cita.setId(1L);
        cita.setEstadoCita(EstadoCita.AGENDADA);

        // Inicializar agenda para evitar NullPointerException
        Agenda agenda = new Agenda();
        agenda.setDia(LocalDate.now()); // ejemplo, según tu modelo
        cita.setAgenda(agenda);

        Paciente paciente = new Paciente();
        paciente.setId(1L);
        cita.setPaciente(paciente);

        dtoCorrecto = new CambiarEstadoCitaDto(1L);
    }

    // Cambio de estado correcto
    @Test
    void cambiarEstadoCita_Correcto() throws ElementoNoEncontradoException {
        // Configurar mock para devolver la cita existente
        when(citaRepo.findById(1L)).thenReturn(Optional.of(cita));

        // Ejecutar método
        citasService.cambiarEstadoCita(dtoCorrecto);

        // Verificar que el estado cambió correctamente
        assertEquals(EstadoCita.EN_REVISION, cita.getEstadoCita());

        // Verificar que se guardó la cita actualizada
        verify(citaRepo).save(cita);
    }

    // La cita no existe
    @Test
    void cambiarEstadoCita_CitaNoEncontrada_LanzaExcepcion() {
        when(citaRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                ElementoNoEncontradoException.class,
                () -> citasService.cambiarEstadoCita(dtoCorrecto)
        );

        // Verificar que no se guardó nada
        verify(citaRepo, never()).save(any());
    }

    // DTO con ID nulo
    @Test
    void cambiarEstadoCita_IdNulo_LanzaExcepcion() {
        CambiarEstadoCitaDto dtoInvalido = new CambiarEstadoCitaDto(null);

        assertThrows(
                ElementoNoEncontradoException.class,
                () -> citasService.cambiarEstadoCita(dtoInvalido)
        );

        // Verificar que no se intentó guardar ninguna cita
        verify(citaRepo, never()).save(any());
    }

}
