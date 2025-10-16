package medicoTest;

import co.edu.uniquindio.dto.medico.EditarMedicoDto;
import co.edu.uniquindio.exceptions.ElementoNoCoincideException;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.mapper.users.MedicoMapper;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Pruebas unitarias para el método actualizarMedico() del servicio MedicoServiceImpl
public class TestEditarMedico {

    @Mock
    private MedicoRepo medicoRepo;

    @Mock
    private MedicoMapper medicoMapper;

    @InjectMocks
    private MedicoServiceImpl medicoService;

    private Medico medico;
    private EditarMedicoDto dtoCorrecto;
    private EditarMedicoDto dtoPasswordIncorrecto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);


        User user = new User();
        user.setEmail("doctor@mail.com");
        user.setPassword("clave123");

        medico = new Medico();
        medico.setId(1L);
        medico.setNombre("Dr. Juan");
        medico.setUser(user);

        dtoCorrecto = new EditarMedicoDto(1L, "clave123", "Dr. Juan Actualizado");

        dtoPasswordIncorrecto = new EditarMedicoDto(1L, "incorrecta", "Dr. Juan Falso");
    }


    // Actualización correcta (password coincide)
    @Test
    void actualizarMedico_Correcto() throws Exception {
        // Configurar mocks
        when(medicoRepo.findById(1L)).thenReturn(Optional.of(medico));

        // Ejecutar método
        medicoService.actualizarMedico(dtoCorrecto);

        // Verificar que el mapper actualizó los datos del médico
        verify(medicoMapper).updateEntity(dtoCorrecto, medico);

        // Verificar que se guardó el médico actualizado
        verify(medicoRepo).save(medico);
    }


    // Password no coincide
    @Test
    void actualizarMedico_PasswordIncorrecto_LanzaExcepcion() {
        when(medicoRepo.findById(1L)).thenReturn(Optional.of(medico));

        // Verificar que lanza la excepción
        ElementoNoCoincideException ex = assertThrows(
                ElementoNoCoincideException.class,
                () -> medicoService.actualizarMedico(dtoPasswordIncorrecto)
        );

        assertEquals("El password no coincide", ex.getMessage());

        // Verificar que NO se guardó el médico
        verify(medicoRepo, never()).save(any());
        verify(medicoMapper, never()).updateEntity(any(), any());
    }

    // Médico no encontrado
    @Test
    void actualizarMedico_MedicoNoEncontrado_LanzaExcepcion() {
        when(medicoRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                ElementoNoEncontradoException.class,
                () -> medicoService.actualizarMedico(dtoCorrecto)
        );

        // Verificar que no se llamó al mapper ni al repo.save()
        verify(medicoMapper, never()).updateEntity(any(), any());
        verify(medicoRepo, never()).save(any());
    }
}


