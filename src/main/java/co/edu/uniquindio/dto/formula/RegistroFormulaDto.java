package co.edu.uniquindio.dto.formula;

import co.edu.uniquindio.dto.detalleFormula.RegistroDetalleFormulaDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record RegistroFormulaDto(


        @NotNull (message = "El Id del Paciente es obligatorio")
        Long idPaciente,
        @NotNull (message = "El Id de la Cita es obligatorio")
        Long idCita,
        @NotEmpty(message = "Debe registrar al menos un detalle de la formula.")
        @Size(min = 1, message = "Debe haber al menos un detalle de la formula.")
        @Valid
        List<RegistroDetalleFormulaDto> detallesFormula

) {
}
