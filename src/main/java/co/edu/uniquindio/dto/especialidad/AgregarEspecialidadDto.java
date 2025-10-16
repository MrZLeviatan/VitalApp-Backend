package co.edu.uniquindio.dto.especialidad;

import jakarta.validation.constraints.NotBlank;

public record AgregarEspecialidadDto(

        @NotBlank (message = "El nombre de la especialidad es obligatorio")
        String especialidad
) {
}
