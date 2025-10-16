package co.edu.uniquindio.security;

import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.models.users.Admin;
import co.edu.uniquindio.models.users.Medico;
import co.edu.uniquindio.models.users.Paciente;
import co.edu.uniquindio.models.users.Persona;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Component
public class JWTUtils {

    // Clave secreta usada para firmar los tokens (mínimo 256 bits para HS256)
    private static final String SECRET = "medicina-app-key-para-firmar-tokens-jwt-de-forma-segura";


    // Genera un token JWT con los datos proporcionados. Este token es utilizado para autenticar y autorizar al usuario en el sistema.
    public String generateToken(String id, Map<String, String> claims) {

        // Se obtiene el instante actual para usar como fecha de emisión y para la expiración
        Instant now = Instant.now(); // Se obtiene el instante actual

        // Se construye el token JWT con los datos proporcionados y firma con una clave secreta
        return Jwts.builder()
                .subject(id) // ID del usuario
                .issuedAt(Date.from(now)) // Fecha de emisión
                .expiration(Date.from(now.plus(1L, ChronoUnit.HOURS))) // Expira en 1 hora
                .claims(claims) // Añade los claims personalizados como el tipo de usuario
                .signWith(getKey(), Jwts.SIG.HS256) // Firma el token con HS256
                .compact();
    }

    public Map<String,String> generarTokenLogin(Persona persona)
            throws ElementoNoEncontradoException {

        // Mapa que asocia las clases concretas con su respectivo rol en formato ROLE_*
        Map<Class<?>, String> rolesPorClase = Map.of(
                Paciente.class, "ROLE_PACIENTE",
                Medico.class, "ROLE_MEDICO",
                Admin.class, "ROLE_ADMIN");

        String rol = rolesPorClase.get(persona.getClass());

        if (rol == null) {
            throw new ElementoNoEncontradoException(
                    "No se encontró un rol para la clase: " + persona.getClass().getSimpleName()
            );
        }

        // Retornar un mapa con los datos del token: email, nombre y rol
        return Map.of(
                "email", persona.getUser().getEmail(),
                "nombre", persona.getNombre(),
                "rol", rol);
    }


    // Válida y analiza un token JWT recibido.
    public Jws<Claims> parseJwt(String jwtString) throws JwtException {
        JwtParser parser = Jwts.parser()
                .verifyWith(getKey()) // Verifica la firma
                .build();

        return parser.parseSignedClaims(jwtString); // Devuelve los claims seguros (firmados)
    }


    // Obtiene la clave secreta HMAC usada para firmar/verificar los tokens.
    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }


}
