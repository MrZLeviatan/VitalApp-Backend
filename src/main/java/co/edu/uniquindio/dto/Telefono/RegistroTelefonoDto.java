package co.edu.uniquindio.dto.Telefono;

import jakarta.validation.constraints.NotBlank;

public record RegistroTelefonoDto(

        @NotBlank (message = "El teléfono del usuario es obligatorio")
        String numero

) {
}
