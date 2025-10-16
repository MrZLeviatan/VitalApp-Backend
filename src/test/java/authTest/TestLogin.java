package authTest;

import co.edu.uniquindio.dto.Auth.LoginDto;
import co.edu.uniquindio.dto.TokenDto;
import co.edu.uniquindio.exceptions.ElementoNoCoincideException;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.exceptions.ElementoNoValidoException;
import co.edu.uniquindio.models.enums.EstadoUser;
import co.edu.uniquindio.models.tools.User;
import co.edu.uniquindio.models.users.Paciente;
import co.edu.uniquindio.security.JWTUtils;
import co.edu.uniquindio.service.utils.PersonaUtilsService;
import co.edu.uniquindio.service.utils.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestLogin {

    @Mock
    private JWTUtils jwtUtils;

    @Mock
    private PersonaUtilsService personaUtilsService;

    @InjectMocks
    private AuthServiceImpl authService;

    private Paciente paciente;
    private LoginDto loginDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // 🇪🇸 Configuramos un usuario simulado con estado activo
        // 🇬🇧 We configure a mock user with ACTIVE state
        User user = new User();
        user.setEmail("test@email.com");
        user.setPassword("12345");
        user.setEstadoUser(EstadoUser.ACTIVO);

        paciente = new Paciente();
        paciente.setId(1L);
        paciente.setUser(user);

        loginDto = new LoginDto("test@email.com", "12345");
    }

    @Test
    void login_exito() throws Exception {
        // Simula que se encuentra la persona por su email
        when(personaUtilsService.buscarPersonaEmail(loginDto.email())).thenReturn(paciente);

        // Simula el mapa que representa el payload del token
        Map<String, String> payloadMock = Map.of("id", "1", "role", "PACIENTE");

        // Simula la generación de token
        when(jwtUtils.generarTokenLogin(paciente)).thenReturn(payloadMock);
        when(jwtUtils.generateToken(anyString(), anyMap())).thenReturn("fake-token");

        // Ejecuta el método de login
        TokenDto result = authService.login(loginDto);

        // Verifica que se generó el token correctamente
        assertNotNull(result);
        assertEquals("fake-token", result.getToken());

        // Verifica que los mocks se llamaron correctamente
        verify(personaUtilsService, times(1)).buscarPersonaEmail(loginDto.email());
        verify(jwtUtils, times(1)).generateToken(anyString(), anyMap());
    }


    @Test
    void login_usuarioEliminado_lanzaExcepcion() throws Exception {
        // Simula un usuario eliminado
        paciente.getUser().setEstadoUser(EstadoUser.ELIMINADO);
        when(personaUtilsService.buscarPersonaEmail(loginDto.email())).thenReturn(paciente);

        // Verifica que lanza la excepción correcta
        assertThrows(ElementoNoValidoException.class, () -> authService.login(loginDto));

        verify(jwtUtils, never()).generateToken(anyString(), anyMap());
    }


    @Test
    void login_passwordNoCoincide_lanzaExcepcion() throws Exception {
        // Simula un password incorrecto
        loginDto = new LoginDto("test@email.com", "wrongpass");
        when(personaUtilsService.buscarPersonaEmail(loginDto.email())).thenReturn(paciente);

        // Verifica que lanza la excepción ElementoNoCoincideException
        assertThrows(ElementoNoCoincideException.class, () -> authService.login(loginDto));

        verify(jwtUtils, never()).generateToken(anyString(), anyMap());
    }

    @Test
    void login_personaNoEncontrada_lanzaExcepcion() throws Exception {
        // Simula que no se encuentra la persona
        when(personaUtilsService.buscarPersonaEmail(loginDto.email()))
                .thenThrow(new ElementoNoEncontradoException("Persona no encontrada"));

        // Verifica que lanza ElementoNoEncontradoException
        assertThrows(ElementoNoEncontradoException.class, () -> authService.login(loginDto));

        verify(jwtUtils, never()).generateToken(anyString(), anyMap());
    }

}
