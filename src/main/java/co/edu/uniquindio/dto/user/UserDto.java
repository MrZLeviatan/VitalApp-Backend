package co.edu.uniquindio.dto.user;

import co.edu.uniquindio.models.enums.EstadoUser;

public record UserDto(

        String email,
        String password,
        EstadoUser estadoUser

) {
}
