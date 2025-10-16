package co.edu.uniquindio.dto.Auth;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(

        @NotBlank (message = "El email es obligatorio")
        String email,
        @NotBlank(message = "El password es obligatorio")
        String password

) {
}
