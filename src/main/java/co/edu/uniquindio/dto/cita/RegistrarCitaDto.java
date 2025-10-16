package co.edu.uniquindio.dto.cita;

import jakarta.validation.constraints.NotNull;

public record RegistrarCitaDto(

        String observaciones,
        @NotNull (message = "Ël Id del Paciente es obligatorio")
        Long idPaciente,
        @NotNull (message = "Ël Id del Medico es obligatorio")
        Long idMedico,
        @NotNull (message = "Ël Id de la Agenda es obligatorio")
        Long idAgenda

) {
}
