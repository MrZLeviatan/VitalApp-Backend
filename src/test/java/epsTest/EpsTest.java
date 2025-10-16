package epsTest;

import co.edu.uniquindio.dto.eps.EpsDto;
import co.edu.uniquindio.dto.eps.RegistrarEpsDto;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.mapper.tools.EpsMapper;
import co.edu.uniquindio.models.objects.Eps;
import co.edu.uniquindio.repository.objects.EpsRepo;
import co.edu.uniquindio.service.utils.impl.EpsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EpsTest {

    @Mock
    private EpsRepo epsRepo;

    @Mock
    private EpsMapper epsMapper;

    @InjectMocks
    private EpsServiceImpl epsService;

    private Eps epsExistente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Eps de ejemplo
        epsExistente = new Eps();
        epsExistente.setId(1L);
        epsExistente.setNombre("SaludTotal");
        epsExistente.setPacientes(new ArrayList<>());
    }


    // ------------------ REGISTRAR EPS ------------------
    @Test
    void registrarEps_Correcto() {
        // DTO de registro
        RegistrarEpsDto dto = new RegistrarEpsDto("NuevaEps");

        // Mocker: el mapper convierte DTO a entidad
        Eps epsNuevo = new Eps();
        epsNuevo.setNombre(dto.nombre());
        when(epsMapper.toEntity(dto)).thenReturn(epsNuevo);

        // Ejecutar método
        epsService.registrarEps(dto);

        // Verificar que se guardó en repo
        verify(epsRepo).save(epsNuevo);
    }

    // ------------------ OBTENER EPS DTO ------------------
    @Test
    void obtenerEpsDto_Correcto() throws ElementoNoEncontradoException {
        // Configurar repo para devolver eps existente
        when(epsRepo.findById(1L)).thenReturn(Optional.of(epsExistente));

        // Configurar mapper para convertir a DTO
        EpsDto epsDto = new EpsDto(epsExistente.getId(), epsExistente.getNombre(), null);
        when(epsMapper.toDto(epsExistente)).thenReturn(epsDto);

        // Ejecutar método
        EpsDto resultado = epsService.obtenerEpsDto(1L);

        // Verificar resultado
        assertEquals("SaludTotal", resultado.nombre());
        assertEquals(1L, resultado.id());
    }

    @Test
    void obtenerEpsDto_EpsNoEncontrada_LanzaExcepcion() {
        // Configurar repo para no encontrar eps
        when(epsRepo.findById(1L)).thenReturn(Optional.empty());

        // Verificar que lanza ElementoNoEncontradoException
        assertThrows(ElementoNoEncontradoException.class, () -> epsService.obtenerEpsDto(1L));
    }

    // ------------------ ENCONTRAR EPS ------------------
    @Test
    void encontrarEps_Correcto() throws ElementoNoEncontradoException {
        when(epsRepo.findById(1L)).thenReturn(Optional.of(epsExistente));

        Eps resultado = epsService.encontrarEps(1L);

        assertEquals("SaludTotal", resultado.getNombre());
        assertEquals(1L, resultado.getId());
    }

    @Test
    void encontrarEps_NoExistente_LanzaExcepcion() {
        when(epsRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ElementoNoEncontradoException.class, () -> epsService.encontrarEps(1L));
    }
}
