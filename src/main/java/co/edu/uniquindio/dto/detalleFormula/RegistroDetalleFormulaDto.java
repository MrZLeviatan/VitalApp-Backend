package co.edu.uniquindio.dto.detalleFormula;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistroDetalleFormulaDto(

        @NotNull (message = "La cantidad del detalle es obligatorio")
        Integer cantidad,
        @NotBlank (message = "Las observaciones del detalle son obligatorias")
        String observaciones,
        @NotBlank (message = "La dosis del detalle es obligatorio")
        String dosis,
        @NotNull (message = "El Id del Medico es obligatorio")
        Long idMedicamento

) {
}
