package co.edu.uniquindio.dto;

// Dto funcional para la mensajería de errores o procedimientos (esto sirve como intermediario para comunicarse entre EndPoints)
public record MensajeDto<T>(boolean error, T mensaje) {
}
