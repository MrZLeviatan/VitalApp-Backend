package co.edu.uniquindio.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CrearUserDto(

        @Email (message = "El email no tiene formato valido")
        @NotBlank (message = "El email del usuario es obligatorio")
        String email,
        @NotBlank (message = "El password del usuario es obligatorio")
        @Size(min = 8, message = "Debe haber al menos 8 caracteres en el password")
        String password

) {
}
