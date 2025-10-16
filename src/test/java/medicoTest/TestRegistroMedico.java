package medicoTest;

import co.edu.uniquindio.dto.Telefono.RegistroTelefonoDto;
import co.edu.uniquindio.dto.medico.RegistrarMedicoDto;
import co.edu.uniquindio.dto.user.CrearUserDto;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.exceptions.ElementoRepetidoException;
import co.edu.uniquindio.mapper.users.MedicoMapper;
import co.edu.uniquindio.models.tools.Especialidad;
import co.edu.uniquindio.models.users.Medico;
import co.edu.uniquindio.repository.users.MedicoRepo;
import co.edu.uniquindio.service.objects.AgendaService;
import co.edu.uniquindio.service.users.impl.MedicoServiceImpl;
import co.edu.uniquindio.service.utils.EspecialidadService;
import co.edu.uniquindio.service.utils.PersonaUtilsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class TestRegistroMedico {

    @Mock
    private PersonaUtilsService personaUtilsService;

    @Mock
    private EspecialidadService especialidadService;

    @Mock
    private AgendaService agendaService;

    @Mock
    private MedicoMapper medicoMapper;

    @Mock
    private MedicoRepo medicoRepo;

    @InjectMocks
    private MedicoServiceImpl medicoService;

    private RegistrarMedicoDto registrarMedicoDto;
    private Especialidad especialidad;
    private Medico medico;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear DTOs simulados
        CrearUserDto crearUserDto = new CrearUserDto("medico@mail.com", "12345");
        RegistroTelefonoDto telefonoDto = new RegistroTelefonoDto("3001112233");

        registrarMedicoDto = new RegistrarMedicoDto(
                "Dr. Leviatan",
                crearUserDto,
                List.of(telefonoDto),
                1L // idEspecialidad
        );

        // Entidades simuladas
        especialidad = new Especialidad();
        especialidad.setId(1L);
        especialidad.setEspecialidad("Cardiología");

        medico = new Medico();
        medico.setId(10L);
        medico.setNombre("Dr. Leviatan");
    }

    // Registro exitoso
    @Test
    void registrarMedico_Exitoso() throws ElementoRepetidoException, ElementoNoEncontradoException {
        // Configurar mocks
        doNothing().when(personaUtilsService).validarEmailNoRepetido(anyString());
        doNothing().when(personaUtilsService).validarTelefonoNoRepetido(anyString());
        when(medicoMapper.toDto(any(RegistrarMedicoDto.class))).thenReturn(medico);
        when(especialidadService.obtenerEspecialidad(1L)).thenReturn(especialidad);

        // Ejecutar método
        medicoService.registrarMedico(registrarMedicoDto);

        // Verificaciones
        verify(personaUtilsService).validarEmailNoRepetido("medico@mail.com");
        verify(personaUtilsService).validarTelefonoNoRepetido("3001112233");
        verify(especialidadService).obtenerEspecialidad(1L);
        verify(medicoRepo).save(medico);
        verify(agendaService).generarAgendaMensual(medico);
    }


    // Email repetido
    @Test
    void registrarMedico_EmailRepetido_LanzaExcepcion() throws ElementoRepetidoException {
        doThrow(new ElementoRepetidoException("Email repetido"))
                .when(personaUtilsService)
                .validarEmailNoRepetido("medico@mail.com");

        assertThrows(ElementoRepetidoException.class,
                () -> medicoService.registrarMedico(registrarMedicoDto));

        verify(personaUtilsService).validarEmailNoRepetido("medico@mail.com");
        verifyNoInteractions(medicoRepo);
    }


    //  Especialidad no encontrada
    @Test
    void registrarMedico_EspecialidadNoEncontrada_LanzaExcepcion()
            throws ElementoNoEncontradoException, ElementoRepetidoException {
        doNothing().when(personaUtilsService).validarEmailNoRepetido(anyString());
        when(especialidadService.obtenerEspecialidad(1L))
                .thenThrow(new ElementoNoEncontradoException("Especialidad no encontrada"));

        assertThrows(ElementoNoEncontradoException.class,
                () -> medicoService.registrarMedico(registrarMedicoDto));

        verify(especialidadService).obtenerEspecialidad(1L);
        verify(medicoRepo, never()).save(any());
    }
}
