package co.edu.uniquindio.dto.paciente;

import jakarta.validation.constraints.NotNull;


public record EditarPacienteDto(

        @NotNull (message = "El id del Paciente es obligatorio")
        Long id,
        String nombre,
        Long idCiudad
) {
}
