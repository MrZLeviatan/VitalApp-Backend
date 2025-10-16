package co.edu.uniquindio.service.utils;

import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.exceptions.ElementoRepetidoException;
import co.edu.uniquindio.models.tools.Ciudad;
import co.edu.uniquindio.models.users.Persona;

public interface PersonaUtilsService {


     void validarEmailNoRepetido(String email) throws ElementoRepetidoException;

    void validarTelefonoNoRepetido(String telefono) throws ElementoRepetidoException;

    Ciudad buscarCiudadId (Long id) throws ElementoNoEncontradoException;

    Persona buscarPersonaEmail(String email) throws ElementoNoEncontradoException;


}
