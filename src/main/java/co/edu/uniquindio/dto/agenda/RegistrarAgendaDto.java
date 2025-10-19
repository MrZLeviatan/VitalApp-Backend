package co.edu.uniquindio.dto.agenda;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO para registrar nuevos horarios en la agenda de un médico
 */
public record RegistrarAgendaDto(
    
    @NotNull(message = "El ID del médico es requerido")
    Long idMedico,
    
    @NotNull(message = "La fecha es requerida")
    LocalDate dia, // Ejemplo: 2025-10-20
    
    @NotNull(message = "La hora de inicio es requerida")
    LocalTime horaInicio, // Ejemplo: 08:00:00
    
    @NotNull(message = "La hora fin es requerida")
    LocalTime horaFin // Ejemplo: 09:00:00

) {
}

