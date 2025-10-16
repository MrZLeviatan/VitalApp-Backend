package co.edu.uniquindio.dto.paciente;

import jakarta.validation.constraints.NotNull;

public record EliminarPacienteDto(

        @NotNull (message = "El Id del paciente es obligatorio")
        Long idPaciente,

        @NotNull (message = "El password del paciente es obligatorio")
        String password
) {
}
