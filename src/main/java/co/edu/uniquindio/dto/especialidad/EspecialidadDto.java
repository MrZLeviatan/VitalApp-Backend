package co.edu.uniquindio.dto.especialidad;

import co.edu.uniquindio.dto.medico.MedicoDto;

import java.util.List;

public record EspecialidadDto(

        Long id,
        String especialidad,
        List<MedicoDto> medicos
) {
}
