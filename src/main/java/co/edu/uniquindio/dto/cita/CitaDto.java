package co.edu.uniquindio.dto.cita;

import co.edu.uniquindio.models.enums.EstadoCita;

public record CitaDto(

        Long id,
        String observaciones,
        EstadoCita estadoCita,
        Long idPaciente,
        Long idMedico,
        Long idFormula,
        Long idAgenda

) {
}
