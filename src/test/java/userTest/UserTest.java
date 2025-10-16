package userTest;

import co.edu.uniquindio.dto.user.CambiarContraseniaDto;
import co.edu.uniquindio.dto.user.EditarUserDto;
import co.edu.uniquindio.exceptions.ElementoNoCoincideException;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.exceptions.ElementoRepetidoException;
import co.edu.uniquindio.models.tools.User;
import co.edu.uniquindio.models.users.Medico;
import co.edu.uniquindio.models.users.Paciente;
import co.edu.uniquindio.repository.users.MedicoRepo;
import co.edu.uniquindio.repository.users.PacienteRepo;
import co.edu.uniquindio.service.users.impl.UserServiceImpl;
import co.edu.uniquindio.service.utils.PersonaUtilsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserTest {

    @Mock
    private PacienteRepo pacienteRepo;

    @Mock
    private MedicoRepo medicoRepo;

    @Mock
    private PersonaUtilsService personaUtilsService;

    @InjectMocks
    private UserServiceImpl userService;

    private Paciente pacienteExistente;
    private Medico medicoExistente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Paciente de ejemplo
        pacienteExistente = new Paciente();
        pacienteExistente.setId(1L);
        User userPaciente = new User();
        userPaciente.setPassword("12345");
        userPaciente.setEmail("paciente@mail.com");
        pacienteExistente.setUser(userPaciente);

        // Médico de ejemplo
        medicoExistente = new Medico();
        medicoExistente.setId(2L);
        User userMedico = new User();
        userMedico.setPassword("54321");
        userMedico.setEmail("medico@mail.com");
        medicoExistente.setUser(userMedico);
    }

    // ------------------ EDITAR EMAIL ------------------
    @Test
    void editarEmail_Paciente_Correcto() throws ElementoRepetidoException, ElementoNoEncontradoException, ElementoNoCoincideException {
        EditarUserDto dto = new EditarUserDto(1L, "nuevo@mail.com", "12345");

        // Configurar mocks
        doNothing().when(personaUtilsService).validarEmailNoRepetido("nuevo@mail.com");
        when(pacienteRepo.findById(1L)).thenReturn(Optional.of(pacienteExistente));

        // Ejecutar método
        userService.editarEmail(dto);

        // Verificar que se actualizó el email
        assert(pacienteExistente.getUser().getEmail().equals("nuevo@mail.com"));

        // Verificar que se guardó
        verify(pacienteRepo).save(pacienteExistente);
    }


    @Test
    void editarEmail_PasswordIncorrecto_LanzaExcepcion() throws ElementoRepetidoException {
        EditarUserDto dto = new EditarUserDto(1L, "paciente@mail.com", "nuevo@mail.com");

        doNothing().when(personaUtilsService).validarEmailNoRepetido("nuevo@mail.com");
        when(pacienteRepo.findById(1L)).thenReturn(Optional.of(pacienteExistente));

        // Cambiamos la contraseña para forzar error
        pacienteExistente.getUser().setPassword("otra");

        assertThrows(ElementoNoCoincideException.class, () -> userService.editarEmail(dto));

        verify(pacienteRepo, never()).save(any());
    }

    @Test
    void editarEmail_UsuarioNoEncontrado_LanzaExcepcion() throws ElementoRepetidoException {
        EditarUserDto dto = new EditarUserDto(10L, "email@mail.com", "nuevo@mail.com");

        doNothing().when(personaUtilsService).validarEmailNoRepetido("nuevo@mail.com");
        when(pacienteRepo.findById(10L)).thenReturn(Optional.empty());
        when(medicoRepo.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ElementoNoEncontradoException.class, () -> userService.editarEmail(dto));
    }

    // ------------------ CAMBIAR PASSWORD ------------------
    @Test
    void cambiarPassword_Paciente_Correcto() throws ElementoNoEncontradoException, ElementoNoCoincideException {
        CambiarContraseniaDto dto = new CambiarContraseniaDto(1L, "12345", "nuevo123");

        when(pacienteRepo.findById(1L)).thenReturn(Optional.of(pacienteExistente));

        userService.cambiarPassword(dto);

        // Verificar que se actualizó el password
        assert(pacienteExistente.getUser().getPassword().equals("nuevo123"));

        verify(pacienteRepo).save(pacienteExistente);
    }

    @Test
    void cambiarPassword_PasswordIncorrecto_LanzaExcepcion() {
        CambiarContraseniaDto dto = new CambiarContraseniaDto(1L, "12345", "nuevo123");

        pacienteExistente.getUser().setPassword("incorrecto");

        when(pacienteRepo.findById(1L)).thenReturn(Optional.of(pacienteExistente));

        assertThrows(ElementoNoCoincideException.class, () -> userService.cambiarPassword(dto));

        verify(pacienteRepo, never()).save(any());
    }

    @Test
    void cambiarPassword_UsuarioNoEncontrado_LanzaExcepcion() {
        CambiarContraseniaDto dto = new CambiarContraseniaDto(10L, "12345", "nuevo123");

        when(pacienteRepo.findById(10L)).thenReturn(Optional.empty());
        when(medicoRepo.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ElementoNoEncontradoException.class, () -> userService.cambiarPassword(dto));
    }
}


