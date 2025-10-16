package co.edu.uniquindio.dto.medico;

import co.edu.uniquindio.dto.Telefono.TelefonoDto;
import co.edu.uniquindio.dto.agenda.AgendaDto;
import co.edu.uniquindio.dto.cita.CitaDto;
import co.edu.uniquindio.dto.user.UserDto;

import java.util.List;

public record MedicoDto(


        Long id,
        String nombre,
        UserDto user,
        List<TelefonoDto> telefonos,
        Long idEspecialidad,
        List<CitaDto> citas,
        List<AgendaDto> agenda


) {
}
