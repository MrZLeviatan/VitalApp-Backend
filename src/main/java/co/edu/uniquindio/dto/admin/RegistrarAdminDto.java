package co.edu.uniquindio.dto.admin;

import co.edu.uniquindio.dto.user.CrearUserDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistrarAdminDto(

        @NotBlank (message = "El nombre del admin es obligatorio")
        String nombre,
        @NotNull (message = "Los datos del usuario son obligatorios")
        @Valid CrearUserDto crearUserDto

) {
}
