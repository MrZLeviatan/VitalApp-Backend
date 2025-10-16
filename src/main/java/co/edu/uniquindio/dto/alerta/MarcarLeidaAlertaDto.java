package co.edu.uniquindio.dto.alerta;

import jakarta.validation.constraints.NotNull;

public record MarcarLeidaAlertaDto(

        @NotNull
        Long idAlerta
) {
}
