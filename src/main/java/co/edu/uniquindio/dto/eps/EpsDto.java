package co.edu.uniquindio.dto.eps;

import co.edu.uniquindio.dto.paciente.PacienteDto;

import java.util.List;

public record EpsDto(

        Long id,
        String nombre,
        List<PacienteDto> pacientes

) {
}
