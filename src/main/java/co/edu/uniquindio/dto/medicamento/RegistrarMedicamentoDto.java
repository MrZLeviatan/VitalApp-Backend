package co.edu.uniquindio.dto.medicamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistrarMedicamentoDto(


        @NotBlank (message = "El nombre del medicamento es obligatorio")
        String nombre,
        @NotNull (message = "El precio del medicamento es obligatorio")
        Double precio

) {
}
