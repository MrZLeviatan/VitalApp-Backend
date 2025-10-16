package co.edu.uniquindio.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDto {

    // Almacena el token de autentificación,
    private String token;

    // Constructor que inicializa el Dto con el token
    public TokenDto(String token) {
        this.token = token;
    }
}
