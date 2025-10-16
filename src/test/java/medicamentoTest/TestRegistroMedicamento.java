package medicamentoTest;

import co.edu.uniquindio.dto.medicamento.RegistrarMedicamentoDto;
import co.edu.uniquindio.mapper.objects.MedicamentoMapper;
import co.edu.uniquindio.models.objects.Medicamento;
import co.edu.uniquindio.repository.objects.MedicamentoRepo;
import co.edu.uniquindio.service.objects.impl.MedicamentoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class TestRegistroMedicamento {

    @Mock
    private MedicamentoMapper medicamentoMapper;

    @Mock
    private MedicamentoRepo medicamentoRepo;

    @InjectMocks
    private MedicamentoServiceImpl medicamentoService;

    private RegistrarMedicamentoDto registrarMedicamentoDto;
    private Medicamento medicamento;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inicializamos el DTO y la entidad simulada
        // Initialize DTO and mock entity
        registrarMedicamentoDto = new RegistrarMedicamentoDto(
                "Paracetamol",
                500.0
        );

        medicamento = new Medicamento();
        medicamento.setNombre("Paracetamol");
        medicamento.setPrecio(500.0);
    }


    @Test
    void registrarMedicamento_deberiaGuardarMedicamentoCorrectamente() {
        // Configuramos el mock del mapper
        // Configure the mapper mock
        when(medicamentoMapper.toEntity(registrarMedicamentoDto)).thenReturn(medicamento);

        // Ejecutamos el método del servicio
        // Execute service method
        medicamentoService.registrarMedicamento(registrarMedicamentoDto);

        // Verificamos que el mapper y el repo fueron llamados correctamente
        // Verify that mapper and repo were called correctly
        verify(medicamentoMapper, times(1)).toEntity(registrarMedicamentoDto);
        verify(medicamentoRepo, times(1)).save(medicamento);
    }
}
