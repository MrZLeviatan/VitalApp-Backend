package co.edu.uniquindio.dto.admin;

import jakarta.validation.constraints.NotNull;

public record EliminarAdminDto(


        @NotNull (message = "Ël Id del medico es obligatorio")
        Long idAdmin
) {
}
