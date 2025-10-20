package co.edu.uniquindio.dto.cita;

import co.edu.uniquindio.models.enums.EstadoCita;

import java.time.LocalDate;
import java.time.LocalTime;

public record CitaDto(

        Long id,
        String observaciones,
        EstadoCita estadoCita,
        
        // Datos del paciente
        Long idPaciente,
        String nombrePaciente,
        
        // Datos del médico
        Long idMedico,
        String nombreMedico,
        String especialidadMedico,
        
        // Datos de la agenda
        Long idAgenda,
        LocalDate fecha,
        LocalTime horaInicio,
        LocalTime horaFin,
        
        // Formula (opcional)
        Long idFormula

) {
}
