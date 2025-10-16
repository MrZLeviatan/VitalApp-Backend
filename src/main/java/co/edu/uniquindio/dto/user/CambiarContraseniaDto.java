package co.edu.uniquindio.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CambiarContraseniaDto(


        @NotNull (message = "Es obligatorio el Id")
        Long id,
        @NotBlank (message = "El antiguo password es obligatorio")
        String antiguoPassword,
        @NotBlank (message = "El nuevo password es obligatorio")
        @Size(min = 8, message = "Debe haber al menos 8 caracteres en el password")
        String nuevoPassword



) {
}
