package co.edu.uniquindio.dto.admin;

import co.edu.uniquindio.dto.user.UserDto;

public record AdminDto(

        Long id,
        String nombre,
        UserDto user

) {
}
