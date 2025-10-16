package co.edu.uniquindio.dto.detalleFormula;

public record DetalleFormulaDto(


       Long id,
       Long idFormula,
       Integer cantidad,
       String observaciones,
       String dosis,
       Long idMedicamento

) {
}
