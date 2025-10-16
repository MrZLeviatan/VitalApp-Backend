package co.edu.uniquindio.dto.alerta;


import co.edu.uniquindio.models.enums.TipoAlerta;

import java.time.LocalDateTime;

public record AlertaDto(

        Long id,
        TipoAlerta tipoAlerta,
        String mensaje,
        LocalDateTime fechaRegistro,
        boolean isLeida,
        Long idPaciente

) {
}
