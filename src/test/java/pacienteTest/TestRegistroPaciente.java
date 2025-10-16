package pacienteTest;

import co.edu.uniquindio.dto.Telefono.RegistroTelefonoDto;
import co.edu.uniquindio.dto.paciente.RegistrarPacienteDto;
import co.edu.uniquindio.dto.user.CrearUserDto;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.exceptions.ElementoRepetidoException;
import co.edu.uniquindio.mapper.users.PacienteMapper;
import co.edu.uniquindio.models.objects.Eps;
import co.edu.uniquindio.models.users.Paciente;
import co.edu.uniquindio.repository.users.PacienteRepo;
import co.edu.uniquindio.service.users.impl.PacienteServiceImpl;
import co.edu.uniquindio.service.utils.EpsService;
import co.edu.uniquindio.service.utils.PersonaUtilsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


// Pruebas unitarias del Servicio PacienteServicio
public class TestRegistroPaciente {


    @Mock
    private PacienteMapper pacienteMapper;

    @Mock
    private PacienteRepo pacienteRepo;

    @Mock
    private PersonaUtilsService personaUtilsService;

    @Mock
    private EpsService epsService;

    @InjectMocks
    private PacienteServiceImpl pacienteService;

    private RegistrarPacienteDto pacienteValidoDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear un DTO válido de registro de paciente
        pacienteValidoDto = new RegistrarPacienteDto(
                "Juan Perez",
                new CrearUserDto("juan@mail.com", "12349560"),
                1L,
                1L,
                List.of(new RegistroTelefonoDto("3001112223"))
        );
    }


    // Test correcto sobre el registro de Pacientes
    @Test
    public void registrarPaciente_Correcto() throws ElementoRepetidoException, ElementoNoEncontradoException {
        // Configurar mocks
        Eps eps = new Eps();
        when(epsService.encontrarEps(1L)).thenReturn(eps);
        when(pacienteMapper.toEntity(any())).thenReturn(new Paciente());

        // Ejecutar método
        pacienteService.registrarPaciente(pacienteValidoDto);

        // Verificar que se validó el email y teléfono
        verify(personaUtilsService).validarEmailNoRepetido("juan@mail.com");
        verify(personaUtilsService).validarTelefonoNoRepetido("3001112223");

        // Verificar que se guardó el paciente en el repo
        verify(pacienteRepo).save(any(Paciente.class));

        // Verificar que se agregó el paciente a la EPS
        verify(epsService).agregarPacienteEps(eq(eps), any(Paciente.class));
    }


    // Test que falla pues el email esta repetido
    @Test
    void registrarPaciente_EmailRepetido_LanzaExcepcion() throws ElementoRepetidoException {
        // Configurar mock para lanzar excepción al validar email
        doThrow(new ElementoRepetidoException("Email ya registrado"))
                .when(personaUtilsService).validarEmailNoRepetido("juan@mail.com");

        // Verificar que se lanza la excepción
        ElementoRepetidoException excepcion = assertThrows(
                ElementoRepetidoException.class,
                () -> pacienteService.registrarPaciente(pacienteValidoDto)
        );

        assertEquals("Email ya registrado", excepcion.getMessage());

        // Verificar que no se guardó el paciente
        verify(pacienteRepo, never()).save(any());
        verify(epsService, never()).agregarPacienteEps(any(), any());
    }


    // Test falla, pues el teléfono está repetido
    @Test
    void registrarPaciente_TelefonoRepetido_LanzaExcepcion() throws ElementoRepetidoException {
        // Configurar mock para email correcto pero teléfono repetido
        doNothing().when(personaUtilsService).validarEmailNoRepetido("juan@mail.com");
        doThrow(new ElementoRepetidoException("Teléfono ya registrado"))
                .when(personaUtilsService).validarTelefonoNoRepetido("3001112223");

        // Verificar que se lanza la excepción
        ElementoRepetidoException excepcion = assertThrows(
                ElementoRepetidoException.class,
                () -> pacienteService.registrarPaciente(pacienteValidoDto)
        );

        assertEquals("Teléfono ya registrado", excepcion.getMessage());

        // Verificar que no se guardó el paciente
        verify(pacienteRepo, never()).save(any());
        verify(epsService, never()).agregarPacienteEps(any(), any());
    }

}
