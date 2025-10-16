package medicoTest;

import co.edu.uniquindio.dto.medico.EliminarMedicoDto;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.mapper.users.MedicoMapper;
import co.edu.uniquindio.models.enums.EstadoUser;
import co.edu.uniquindio.models.tools.User;
import co.edu.uniquindio.models.users.Medico;
import co.edu.uniquindio.repository.users.MedicoRepo;
import co.edu.uniquindio.service.users.impl.MedicoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TestEliminarMedico {


    @Mock
    private MedicoRepo medicoRepo;

    @Mock
    private MedicoMapper medicoMapper;

    @InjectMocks
    private MedicoServiceImpl medicoService;

    private Medico medico;
    private EliminarMedicoDto eliminarMedicoDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Simular entidad Medico con usuario activo
        User user = new User();
        user.setEmail("doctor@mail.com");
        user.setPassword("12345");
        user.setEstadoUser(EstadoUser.ACTIVO);

        medico = new Medico();
        medico.setId(100L);
        medico.setNombre("Dr. Leviatan");
        medico.setUser(user);

        // DTO de eliminación
        eliminarMedicoDto = new EliminarMedicoDto(100L);
    }

    // Eliminación exitosa
    @Test
    void eliminarMedico_Exitoso() throws ElementoNoEncontradoException {
        // Configuramos el mock del repositorio
        when(medicoRepo.findById(100L)).thenReturn(Optional.of(medico));

        // Ejecutamos el método
        medicoService.eliminarMedico(eliminarMedicoDto);

        // Verificamos que el estado del usuario cambió a ELIMINADO
        assertEquals(EstadoUser.ELIMINADO, medico.getUser().getEstadoUser(),
                "El estado del usuario debe ser ELIMINADO");

        // Verificamos que se guardó en la base de datos
        verify(medicoRepo).save(medico);
    }


    // Médico no encontrado
    @Test
    void eliminarMedico_NoExiste_LanzaExcepcion() {
        // Simulamos que el médico no existe
        when(medicoRepo.findById(100L)).thenReturn(Optional.empty());

        // Verificamos que se lanza la excepción esperada
        ElementoNoEncontradoException exception = assertThrows(
                ElementoNoEncontradoException.class,
                () -> medicoService.eliminarMedico(eliminarMedicoDto)
        );

        assertEquals("Médico no registrado en el sistema.", exception.getMessage(),
                "El mensaje de la excepción debe ser correcto");

        // Verificamos que no se intenta guardar nada
        verify(medicoRepo, never()).save(any());
    }
}
