package co.edu.uniquindio.dto.admin;

import jakarta.validation.constraints.NotNull;

public record EditarAdminDto(

        @NotNull (message = "Ël Id del medico es obligatorio")
        Long id,
        String nombre
) {
}
