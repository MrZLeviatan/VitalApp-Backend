package co.edu.uniquindio.controller.paciente;

import co.edu.uniquindio.dto.MensajeDto;
import co.edu.uniquindio.dto.detalleFormula.DetalleFormulaDto;
import co.edu.uniquindio.dto.formula.FormulaDto;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.service.objects.FormulaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/paciente")
public class PacienteFormulaController {


    private final FormulaService formulaService;

    // Obtiene las fórmulas de un paciente
    @GetMapping("{idPaciente}/formula")
    public ResponseEntity<MensajeDto<List<FormulaDto>>> obtenerFormulasPaciente(@PathVariable Long idPaciente)
            throws ElementoNoEncontradoException {

        List<FormulaDto> listaFormulas = formulaService.obtenerFormulasPaciente(idPaciente);
        return ResponseEntity.ok().body(new MensajeDto<>(false, listaFormulas));
    }


    // Obtiene una formula especifica
    @GetMapping("/formula/{idFormula}")
    public ResponseEntity<MensajeDto<FormulaDto>> obtenerFormula(@PathVariable Long idFormula)
            throws ElementoNoEncontradoException {

        FormulaDto formulaDto = formulaService.obtenerFormulaDto(idFormula);
        return ResponseEntity.ok().body(new MensajeDto<>(false, formulaDto));
    }


    // Obtiene los detalles de una fórmula
    @GetMapping("formula/{idFormula}/detalles")
    public ResponseEntity<MensajeDto<List<DetalleFormulaDto>>> obtenerDetallesFormula(@PathVariable Long idFormula)
            throws ElementoNoEncontradoException {

        List<DetalleFormulaDto> detalleFormulaDtos = formulaService.obtenerDetalleFormula(idFormula);
        return ResponseEntity.ok().body(new MensajeDto<>(false, detalleFormulaDtos));
    }
}
