package co.edu.uniquindio.service.utils;

import co.edu.uniquindio.dto.especialidad.AgregarEspecialidadDto;
import co.edu.uniquindio.dto.especialidad.EspecialidadDto;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.models.tools.Especialidad;
import co.edu.uniquindio.models.users.Medico;

import java.util.List;


public interface EspecialidadService {


    // (ADMIN)
    void registrarEspecialidad (AgregarEspecialidadDto agregarEspecialidadDto);

    // SISTEMA
    Especialidad obtenerEspecialidad(Long idEspecialidad)
            throws ElementoNoEncontradoException;

    // (ADMIN)
    EspecialidadDto obtenerEspecialidadDto (Long idEspecialidad)
            throws ElementoNoEncontradoException;

    // (ADMIN, CITA PARA PACIENTE)
    List<EspecialidadDto> obtenerEspecialidades();

    // SISTEMA
    void agregarMedicoEspecialidad(Especialidad especialidad, Medico medico);


}
