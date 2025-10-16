package co.edu.uniquindio.dto.medico;

import jakarta.validation.constraints.NotNull;


public record EditarMedicoDto(

        @NotNull (message = "El id del Medico es obligatorio")
        Long id,
        @NotNull (message = "El password del Medico es obligatorio")
        String password,
        String nombre
) {
}
