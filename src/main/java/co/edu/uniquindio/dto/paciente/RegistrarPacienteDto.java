package co.edu.uniquindio.dto.paciente;

import co.edu.uniquindio.dto.Telefono.RegistroTelefonoDto;
import co.edu.uniquindio.dto.user.CrearUserDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record RegistrarPacienteDto(

        @NotBlank(message = "El nombre del paciente es obligatorio")
        String nombre,
        @NotNull (message = "Los datos del usuario son obligatorios")
        @Valid CrearUserDto crearUserDto,
        @NotNull (message = "Debe especificar la EPS del paciente.")
        Long idEps,
        @NotNull(message = "Debe especificar la ciudad del paciente.")
        Long idCiudad,
        @NotEmpty(message = "Debe registrar al menos un número telefónico.")
        @Size(min = 1, message = "Debe haber al menos un teléfono registrado.")
        @Valid
        List<RegistroTelefonoDto> telefonos


) {
}
