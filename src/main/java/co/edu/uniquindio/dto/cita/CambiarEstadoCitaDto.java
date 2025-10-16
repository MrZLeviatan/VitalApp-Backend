package co.edu.uniquindio.dto.cita;

import jakarta.validation.constraints.NotNull;

// Cambiar el estado de una cita: Paciente (cancelar), Medico(En_Revision)
public record CambiarEstadoCitaDto(

        @NotNull (message = "Ël Id de la cita es obligatorio")
        Long idCita
) {
}
