package co.edu.uniquindio.dto.paciente;

import co.edu.uniquindio.dto.Telefono.TelefonoDto;
import co.edu.uniquindio.dto.cita.CitaDto;
import co.edu.uniquindio.dto.formula.FormulaDto;
import co.edu.uniquindio.dto.user.UserDto;

import java.util.List;

public record PacienteDto(


        Long id,
        String nombre,
        UserDto user,
        Long idEps,
        CiudadDto ciudad,
        List<TelefonoDto> telefonos,
        List<CitaDto> citas,
        List<FormulaDto> formulasMedicas

) {
}
