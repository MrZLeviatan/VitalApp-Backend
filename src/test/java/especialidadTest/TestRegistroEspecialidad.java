package especialidadTest;

import co.edu.uniquindio.dto.especialidad.AgregarEspecialidadDto;
import co.edu.uniquindio.mapper.tools.EspecialidadMapper;
import co.edu.uniquindio.models.tools.Especialidad;
import co.edu.uniquindio.repository.objects.EspecialidadRepo;
import co.edu.uniquindio.service.utils.impl.EspecialidadServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;


public class TestRegistroEspecialidad {

    @Mock
    private EspecialidadRepo especialidadRepo;

    @Mock
    private EspecialidadMapper especialidadMapper;

    @InjectMocks
    private EspecialidadServiceImpl especialidadService;

    private AgregarEspecialidadDto agregarEspecialidadDto;
    private Especialidad especialidad;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Creamos el DTO de entrada
        agregarEspecialidadDto = new AgregarEspecialidadDto("Cardiología");

        // Creamos la entidad simulada
        especialidad = new Especialidad();
        especialidad.setId(1L);
        especialidad.setEspecialidad("Cardiología");
    }

    // Registro exitoso
    @Test
    void registrarEspecialidad_Exitoso() {
        // Simulamos que el mapper convierte correctamente el DTO en entidad
        when(especialidadMapper.toEntity(agregarEspecialidadDto)).thenReturn(especialidad);

        // Ejecutamos el método
        especialidadService.registrarEspecialidad(agregarEspecialidadDto);

        // Verificamos que se guardó la especialidad
        verify(especialidadRepo, times(1)).save(especialidad);

        // Verificamos que el mapper fue llamado correctamente
        verify(especialidadMapper, times(1)).toEntity(agregarEspecialidadDto);
    }

}
