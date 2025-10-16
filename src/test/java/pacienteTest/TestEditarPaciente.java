package pacienteTest;

import co.edu.uniquindio.dto.paciente.EditarPacienteDto;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.mapper.users.PacienteMapper;
import co.edu.uniquindio.models.tools.Ciudad;
import co.edu.uniquindio.models.users.Paciente;
import co.edu.uniquindio.repository.users.PacienteRepo;
import co.edu.uniquindio.service.users.impl.PacienteServiceImpl;
import co.edu.uniquindio.service.utils.PersonaUtilsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

// Pruebas unitarias del Servicio PacienteServicio
public class TestEditarPaciente {

    @Mock
    private PacienteRepo pacienteRepo;

    @Mock
    private PacienteMapper pacienteMapper;

    @Mock
    private PersonaUtilsService personaUtilsService;

    @InjectMocks
    private PacienteServiceImpl pacienteService;

    private Paciente pacienteExistente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Paciente que existe en el sistema
        pacienteExistente = new Paciente();
        pacienteExistente.setId(1L);
    }


    // Test edición de Paciente correcto
    @Test
    void editarPaciente_Correcto() throws ElementoNoEncontradoException {
        // Configurar mock: al buscar paciente por id, devolvemos paciente existente
        when(pacienteRepo.findById(1L)).thenReturn(java.util.Optional.of(pacienteExistente));

        // DTO con los cambios
        EditarPacienteDto dto = new EditarPacienteDto(1L, "Juan Modificado", 2L);

        // Configurar mock para devolver ciudad
        when(personaUtilsService.buscarCiudadId(2L)).thenReturn(new Ciudad());

        // Ejecutar método
        pacienteService.editarPaciente(dto);

        // Verificar que se llamó el mapper para actualizar la entidad
        verify(pacienteMapper).updateEntity(dto, pacienteExistente);

        // Verificar que se guardó el paciente
        verify(pacienteRepo).save(pacienteExistente);

        // Verificar que se buscó la ciudad
        verify(personaUtilsService).buscarCiudadId(2L);
    }

    // Test erróneo de la edición de Paciente no Encontrado
    @Test
    void editarPaciente_PacienteNoEncontrado_LanzaExcepcion() {
        // Configurar mock: al buscar paciente por id, no encuentra nada
        when(pacienteRepo.findById(1L)).thenReturn(java.util.Optional.empty());

        EditarPacienteDto dto = new EditarPacienteDto(1L, "Juan Modificado", 2L);

        // Verificar que se lanza ElementoNoEncontradoException
        assertThrows(ElementoNoEncontradoException.class, () -> pacienteService.editarPaciente(dto));

        // Verificar que no se llamó el mapper ni se guardó el paciente
        verify(pacienteMapper, never()).updateEntity(any(), any());
        verify(pacienteRepo, never()).save(any());
    }

    // Test erróneo de la edición de Paciente Ciudad no Existe
    @Test
    void editarPaciente_CiudadNoExistente_LanzaExcepcion() throws ElementoNoEncontradoException {
        // Configurar mock: paciente sí existe
        when(pacienteRepo.findById(1L)).thenReturn(java.util.Optional.of(pacienteExistente));

        // Configurar mock: al buscar ciudad, lanzamos excepción
        when(personaUtilsService.buscarCiudadId(2L))
                .thenThrow(new ElementoNoEncontradoException("Ciudad no encontrada"));

        EditarPacienteDto dto = new EditarPacienteDto(1L, "Juan Modificado", 2L);

        // Verificar que se lanza excepción
        assertThrows(ElementoNoEncontradoException.class, () -> pacienteService.editarPaciente(dto));

        // Verificar que se llamó el mapper
        verify(pacienteMapper).updateEntity(dto, pacienteExistente);

        // Verificar que no se guardó el paciente
        verify(pacienteRepo, never()).save(any());
    }

}
