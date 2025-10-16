package co.edu.uniquindio.dto.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EditarUserDto(

        @NotNull (message = "Es obligatorio el Id")
        Long id,    // Id de la Persona
        @Email (message = "El formato debe ser el de un Email obligatoriamente")
        @NotBlank (message = "El email actualizado no puede estar vació")
        String emailNuevo,
        @NotBlank (message = "El password es obligatorio")
        String password

) {
}
