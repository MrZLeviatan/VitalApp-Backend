package co.edu.uniquindio.service.users;

import co.edu.uniquindio.dto.user.CambiarContraseniaDto;
import co.edu.uniquindio.dto.user.EditarUserDto;
import co.edu.uniquindio.exceptions.ElementoNoCoincideException;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.exceptions.ElementoRepetidoException;

public interface UserService {

    // USO DE PACIENTE,MEDICO Y 'ADMIN'
    void editarEmail(EditarUserDto editarUserDto)
            throws ElementoRepetidoException, ElementoNoEncontradoException, ElementoNoCoincideException;

    // USO DE PACIENTE, MEDICO Y 'ADMIN'
    void cambiarPassword(CambiarContraseniaDto cambiarContraseniaDto)
            throws ElementoNoCoincideException, ElementoNoEncontradoException;

}
