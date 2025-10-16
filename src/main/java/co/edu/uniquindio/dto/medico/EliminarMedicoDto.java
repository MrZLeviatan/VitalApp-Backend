package co.edu.uniquindio.dto.medico;

import jakarta.validation.constraints.NotNull;

public record EliminarMedicoDto(


        @NotNull(message = "El id del Medico es obligatorio")
        Long idMedico
) {
}
