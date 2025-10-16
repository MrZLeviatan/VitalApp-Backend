package formulamedicaTest;


import co.edu.uniquindio.dto.detalleFormula.RegistroDetalleFormulaDto;
import co.edu.uniquindio.dto.formula.RegistroFormulaDto;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.mapper.objects.FormulaMapper;
import co.edu.uniquindio.models.objects.Cita;
import co.edu.uniquindio.models.objects.DetalleFormula;
import co.edu.uniquindio.models.objects.Formula;
import co.edu.uniquindio.models.objects.Medicamento;
import co.edu.uniquindio.models.users.Paciente;
import co.edu.uniquindio.repository.objects.DetalleFormularioRepo;
import co.edu.uniquindio.repository.objects.FormulaRepo;
import co.edu.uniquindio.service.objects.CitasService;
import co.edu.uniquindio.service.objects.MedicamentoService;
import co.edu.uniquindio.service.objects.impl.FormulaServiceImpl;
import co.edu.uniquindio.service.users.PacienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestRegistroFormula {

    @Mock
    private FormulaRepo formulaRepo;

    @Mock
    private FormulaMapper formulaMapper;

    @Mock
    private PacienteService pacienteService;

    @Mock
    private CitasService citasService;

    @Mock
    private MedicamentoService medicamentoService;

    @Mock
    private DetalleFormularioRepo detalleFormularioRepo;

    @InjectMocks
    private FormulaServiceImpl formulaService;

    private RegistroFormulaDto registroFormulaDto;
    private Paciente paciente;
    private Cita cita;
    private Medicamento medicamento;
    private Formula formula;
    private DetalleFormula detalleFormula;

    @BeforeEach
    void setUp() {
        paciente = new Paciente();
        paciente.setId(1L);

        cita = new Cita();
        cita.setId(1L);

        medicamento = new Medicamento();
        medicamento.setId(10L);

        detalleFormula = new DetalleFormula();
        formula = new Formula();
        formula.setDetallesFormula(List.of(detalleFormula));

        RegistroDetalleFormulaDto detalleDto = new RegistroDetalleFormulaDto(10, "1 tableta diaria","Dos cada dia",10L);
        registroFormulaDto = new RegistroFormulaDto(1L, 1L, List.of(detalleDto));
    }

    @Test
    void registrarFormula_exito() throws ElementoNoEncontradoException {
        // Configuración de mocks
        when(formulaMapper.toEntity(registroFormulaDto)).thenReturn(formula);
        when(pacienteService.buscarPacienteId(1L)).thenReturn(paciente);
        when(citasService.obtenerCita(1L)).thenReturn(cita);
        when(medicamentoService.obtenerMedicamentoId(10L)).thenReturn(medicamento);

        // Ejecutamos el método
        formulaService.registrarFormula(registroFormulaDto);

        // Verificamos que se haya guardado la fórmula
        verify(formulaRepo, atLeastOnce()).save(formula);

        // Verificamos asociaciones
       verify(pacienteService).agregarFormulaPaciente(paciente, formula);
        verify(citasService).agregarFormulaCita(cita, formula);

        // Verificamos que se haya buscado el medicamento
        verify(medicamentoService).obtenerMedicamentoId(10L);
    }

    @Test
    void registrarFormula_pacienteNoEncontrado_lanzaExcepcion() throws ElementoNoEncontradoException {
        when(formulaMapper.toEntity(registroFormulaDto)).thenReturn(formula);
        when(pacienteService.buscarPacienteId(1L)).thenThrow(new ElementoNoEncontradoException("No se encontró el paciente"));

        org.junit.jupiter.api.Assertions.assertThrows(ElementoNoEncontradoException.class, () ->
                formulaService.registrarFormula(registroFormulaDto));

        verify(formulaRepo, never()).save(any());
    }


}
