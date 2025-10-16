package co.edu.uniquindio.dto.formula;

import co.edu.uniquindio.dto.detalleFormula.DetalleFormulaDto;

import java.time.LocalDate;
import java.util.List;

public record FormulaDto (


    Long id,
    LocalDate fechaRegistro,
    Long idPaciente,
    Long idCita,
    List<DetalleFormulaDto> detallesFormula

){
}
