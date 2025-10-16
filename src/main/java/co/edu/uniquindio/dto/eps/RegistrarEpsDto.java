package co.edu.uniquindio.dto.eps;

import jakarta.validation.constraints.NotBlank;

public record RegistrarEpsDto(

        @NotBlank (message = "El nombre de la EPS es obligatorio")
        String nombre

) {
}
