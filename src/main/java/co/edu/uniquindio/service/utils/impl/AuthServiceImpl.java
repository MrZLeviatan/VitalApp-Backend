package co.edu.uniquindio.service.utils.impl;

import co.edu.uniquindio.dto.Auth.LoginDto;
import co.edu.uniquindio.dto.TokenDto;
import co.edu.uniquindio.exceptions.ElementoNoCoincideException;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.exceptions.ElementoNoValidoException;
import co.edu.uniquindio.models.enums.EstadoUser;
import co.edu.uniquindio.models.users.Persona;
import co.edu.uniquindio.security.JWTUtils;
import co.edu.uniquindio.service.utils.AuthService;
import co.edu.uniquindio.service.utils.PersonaUtilsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JWTUtils jwtUtils;
    private final PersonaUtilsService personaUtilsService;


    // Método de logueo
    @Override
    public TokenDto login(LoginDto loginDto) throws ElementoNoEncontradoException, ElementoNoValidoException, ElementoNoCoincideException {

        String token;

        // Valida el email de la persona logueada
        Persona personaLogin = personaUtilsService.buscarPersonaEmail(loginDto.email());

        // Verifica si la persona no fue eliminado anteriormente
        if (personaLogin.getUser().getEstadoUser().equals(EstadoUser.ELIMINADO)) {
            throw new ElementoNoValidoException("Usuario eliminado anteriormente");
        }
        // Verifica si el password coincide
        if (!personaLogin.getUser().getPassword().equals(loginDto.password())) {
            throw new ElementoNoCoincideException("Password no coincide");
        }

        // Genera el token de logueo
        token = jwtUtils.generateToken(personaLogin.getId().toString(),jwtUtils.generarTokenLogin(personaLogin));

        return new TokenDto(token);
    }

}
