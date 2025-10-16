package citaTest;

import co.edu.uniquindio.dto.cita.CambiarEstadoCitaDto;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.mapper.objects.CitaMapper;
import co.edu.uniquindio.models.enums.EstadoCita;
import co.edu.uniquindio.models.objects.Agenda;
import co.edu.uniquindio.models.objects.Cita;
import co.edu.uniquindio.repository.objects.AgendaRepo;
import co.edu.uniquindio.repository.objects.CitaRepo;
import co.edu.uniquindio.service.objects.impl.CitaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Pruebas unitarias para el método cancelar cita del servicio CitaService
public class TestCancelarCita {


    @Mock
    private CitaRepo citaRepo;

    @Mock
    private AgendaRepo agendaRepo;

    @Mock
    private CitaMapper citaMapper;

    @InjectMocks
    private CitaServiceImpl citaService;

    private Cita cita;
    private Agenda agenda;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear una agenda con fecha futura (más de 7 días)
        agenda = new Agenda();
        agenda.setId(1L);
        agenda.setDia(LocalDate.now().plusDays(10));
        agenda.setActivo(false);

        // Crear una cita asociada a esa agenda
        cita = new Cita();
        cita.setId(1L);
        cita.setAgenda(agenda);
        cita.setEstadoCita(EstadoCita.AGENDADA);

        agenda.setCita(cita);
    }

    // Cancelar cita con más de 7 días de anticipación → la agenda se libera
    @Test
    void cancelarCita_LiberaAgenda_Correcto() throws ElementoNoEncontradoException {
        CambiarEstadoCitaDto dto = new CambiarEstadoCitaDto(1L);

        when(citaRepo.findById(1L)).thenReturn(java.util.Optional.of(cita));

        // Ejecutar método
        citaService.cancelarCita(dto);

        // Verificar cambios
        assertEquals(EstadoCita.CANCELADA, cita.getEstadoCita());
        assertEquals(true, agenda.isActivo());
        assertEquals(null, agenda.getCita());

        // Verificar guardado en repositorios
        verify(agendaRepo).save(agenda);
        verify(citaRepo).save(cita);
    }


    // Cancelar cita con menos de 7 días → la agenda NO se libera
    @Test
    void cancelarCita_NoLiberaAgenda_SiMenosDe7Dias() throws ElementoNoEncontradoException {
        // Agenda con fecha próxima (3 días)
        agenda.setDia(LocalDate.now().plusDays(3));
        CambiarEstadoCitaDto dto = new CambiarEstadoCitaDto(1L);

        when(citaRepo.findById(1L)).thenReturn(java.util.Optional.of(cita));

        // Ejecutar método
        citaService.cancelarCita(dto);

        // Verificar que la agenda no se libera
        assertEquals(EstadoCita.CANCELADA, cita.getEstadoCita());
        assertEquals(false, agenda.isActivo()); // sigue inactiva
        assertEquals(cita, agenda.getCita());  // sigue asociada

        // agendaRepo.save() no debe llamarse porque no se libera la agenda
        verify(agendaRepo, never()).save(any());
        verify(citaRepo).save(cita);
    }

    // Cita no encontrada - incorrecto
    @Test
    void cancelarCita_CitaNoEncontrada_LanzaExcepcion() {
        CambiarEstadoCitaDto dto = new CambiarEstadoCitaDto(99L);

        when(citaRepo.findById(99L)).thenReturn(java.util.Optional.empty());

        // Verificar que se lanza la excepción
        org.junit.jupiter.api.Assertions.assertThrows(
                ElementoNoEncontradoException.class,
                () -> citaService.cancelarCita(dto)
        );

        // No se debe guardar nada
        verify(citaRepo, never()).save(any());
        verify(agendaRepo, never()).save(any());
    }
}
