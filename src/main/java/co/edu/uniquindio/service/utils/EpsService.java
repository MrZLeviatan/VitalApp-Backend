package co.edu.uniquindio.service.utils;

import co.edu.uniquindio.dto.eps.EpsDto;
import co.edu.uniquindio.dto.eps.RegistrarEpsDto;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.models.objects.Eps;
import co.edu.uniquindio.models.users.Paciente;

import java.util.List;

public interface EpsService {

    // (ADMIN)
    void registrarEps(RegistrarEpsDto registrarEpsDto);

    // (ADMIN)
    EpsDto obtenerEpsDto(Long idEps)
            throws ElementoNoEncontradoException;


    Eps encontrarEps(Long id)
            throws ElementoNoEncontradoException;

    // (ADMIN)
    List<EpsDto> listarEpsDto();

    void agregarPacienteEps(Eps eps,Paciente paciente);

}
