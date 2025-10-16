package co.edu.uniquindio.security;

import co.edu.uniquindio.dto.MensajeDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationEntryPoint  implements org.springframework.security.web.AuthenticationEntryPoint {


    // Método invocado automáticamente por Spring Security cuando un usuario no autenticado intenta acceder a un recurso protegido.
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        // Se crea un objeto de respuesta personalizada con un mensaje de error
        MensajeDto<String> dto = new MensajeDto<>(true, "No tienes los permisos necesarios para" +
                " acceder a este recurso");

        // Se configura la respuesta HTTP para que sea de tipo JSON y con estado 403 (Prohibido)
        response.setContentType("application/json");
        response.setStatus(403); // Código HTTP 403 - Forbidden

        // Se escribe el objeto JSON en la respuesta utilizando Jackson
        response.getWriter().write(new ObjectMapper().writeValueAsString(dto));
        response.getWriter().flush(); // Se asegura de enviar todos los datos
        response.getWriter().close(); // Se cierra el flujo de escritura
    }
}
