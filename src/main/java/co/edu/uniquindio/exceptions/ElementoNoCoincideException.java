package co.edu.uniquindio.exceptions;

public class ElementoNoCoincideException extends Exception {
    // Constructor que recibe un mensaje para describir el error de no coincidencia.
    public ElementoNoCoincideException(String message) {
        super(message);
    }
}