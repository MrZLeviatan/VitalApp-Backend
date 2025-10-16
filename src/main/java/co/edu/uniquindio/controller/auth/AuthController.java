package co.edu.uniquindio.controller.auth;

import co.edu.uniquindio.dto.Auth.LoginDto;
import co.edu.uniquindio.dto.MensajeDto;
import co.edu.uniquindio.dto.TokenDto;
import co.edu.uniquindio.exceptions.ElementoNoCoincideException;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.exceptions.ElementoNoValidoException;
import co.edu.uniquindio.service.utils.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<MensajeDto<TokenDto>> login (@RequestBody LoginDto loginDto)
            throws ElementoNoValidoException, ElementoNoCoincideException, ElementoNoEncontradoException {

        TokenDto tokenDto = authService.login(loginDto);
        return ResponseEntity.ok().body(new MensajeDto<>(false, tokenDto));
    }

}
