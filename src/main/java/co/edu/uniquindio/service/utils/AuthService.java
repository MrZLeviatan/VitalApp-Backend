package co.edu.uniquindio.service.utils;

import co.edu.uniquindio.dto.Auth.LoginDto;
import co.edu.uniquindio.dto.TokenDto;
import co.edu.uniquindio.exceptions.ElementoNoCoincideException;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.exceptions.ElementoNoValidoException;

public interface AuthService {


    TokenDto login (LoginDto loginDto) throws ElementoNoEncontradoException, ElementoNoValidoException, ElementoNoCoincideException;

}
