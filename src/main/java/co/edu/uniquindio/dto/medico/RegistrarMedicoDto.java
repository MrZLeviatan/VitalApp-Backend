package co.edu.uniquindio.dto.medico;

import co.edu.uniquindio.dto.Telefono.RegistroTelefonoDto;
import co.edu.uniquindio.dto.user.CrearUserDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record RegistrarMedicoDto(


        @NotBlank (message = "El nombre del medico es obligatorio")
        String nombre,

        @NotNull (message = "Los datos del medico son obligatorios")
        @Valid CrearUserDto user,

        @NotEmpty(message = "Debe registrar al menos un número telefónico.")
        @Size(min = 1, message = "Debe haber al menos un teléfono registrado.")
        @Valid List<RegistroTelefonoDto> telefonos,

        @NotNull (message = "Debe especificar la especialidad del medico")
        Long idEspecialidad

) {
}
