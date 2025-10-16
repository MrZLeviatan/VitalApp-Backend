package pacienteTest;


import co.edu.uniquindio.dto.paciente.EliminarPacienteDto;
import co.edu.uniquindio.exceptions.ElementoNoCoincideException;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.mapper.users.PacienteMapper;
import co.edu.uniquindio.models.enums.EstadoUser;
import co.edu.uniquindio.models.tools.User;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TestEliminarPaciente {
    @Mock
    private PacienteRepo pacienteRepo;

    @Mock
    private PacienteMapper pacienteMapper;

    @Mock
    private PersonaUtilsService personaUtilsService;

    @Mock
    private EpsService epsService;

    @InjectMocks
    private PacienteServiceImpl pacienteService;

    private Paciente pacienteExistente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Paciente existente con user
        pacienteExistente = new Paciente();
        pacienteExistente.setId(1L);
        User user = new User();
        user.setPassword("12345");
        user.setEstadoUser(EstadoUser.ACTIVO);
        pacienteExistente.setUser(user);
    }

    @Test
    void eliminarPaciente_Correcto() throws ElementoNoEncontradoException, ElementoNoCoincideException {
        // Configurar mock: paciente existe
        when(pacienteRepo.findById(1L)).thenReturn(java.util.Optional.of(pacienteExistente));

        // DTO de eliminación
        EliminarPacienteDto dto = new EliminarPacienteDto(1L, "12345");

        // Ejecutar método
        pacienteService.eliminarPaciente(dto);

        // Verificar que el estado del user se cambió a ELIMINADO
        assert(pacienteExistente.getUser().getEstadoUser() == EstadoUser.ELIMINADO);

        // Verificar que se guardó el paciente
        verify(pacienteRepo).save(pacienteExistente);
    }

    @Test
    void eliminarPaciente_ContraseñaIncorrecta_LanzaExcepcion() throws ElementoNoEncontradoException {
        // Configurar mock: paciente existe
        when(pacienteRepo.findById(1L)).thenReturn(java.util.Optional.of(pacienteExistente));

        EliminarPacienteDto dto = new EliminarPacienteDto(1L, "incorrecta");

        // Verificar que se lanza ElementoNoCoincideException
        assertThrows(ElementoNoCoincideException.class, () -> pacienteService.eliminarPaciente(dto));

        // Verificar que no se guardó el paciente
        verify(pacienteRepo, never()).save(any());
    }

    @Test
    void eliminarPaciente_PacienteNoEncontrado_LanzaExcepcion() {
        // Configurar mock: paciente no existe
        when(pacienteRepo.findById(1L)).thenReturn(java.util.Optional.empty());

        EliminarPacienteDto dto = new EliminarPacienteDto(1L, "12345");

        // Verificar que se lanza ElementoNoEncontradoException
        assertThrows(ElementoNoEncontradoException.class, () -> pacienteService.eliminarPaciente(dto));

        // Verificar que no se llamó save
        verify(pacienteRepo, never()).save(any());
    }

}
