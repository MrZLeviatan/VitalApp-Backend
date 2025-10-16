package co.edu.uniquindio.service.users;

import co.edu.uniquindio.dto.medico.EditarMedicoDto;
import co.edu.uniquindio.dto.medico.EliminarMedicoDto;
import co.edu.uniquindio.dto.medico.MedicoDto;
import co.edu.uniquindio.dto.medico.RegistrarMedicoDto;
import co.edu.uniquindio.exceptions.ElementoNoCoincideException;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.exceptions.ElementoRepetidoException;
import co.edu.uniquindio.models.users.Medico;

import java.util.List;

public interface MedicoService {

    // (ADMIN)
    void registrarMedico(RegistrarMedicoDto registrarMedicoDto)
            throws ElementoRepetidoException, ElementoNoEncontradoException;

    // (MEDICO)
    void actualizarMedico(EditarMedicoDto editarMedicoDto)
            throws ElementoNoEncontradoException, ElementoNoCoincideException;

    // (ADMIN)
    void eliminarMedico(EliminarMedicoDto eliminarMedicoDto)
            throws ElementoNoEncontradoException;

    // (ADMIN,MEDICO)
    MedicoDto obtenerMedicoId(Long id)
            throws ElementoNoEncontradoException;

    // (ADMIN,MEDICO)
    MedicoDto obtenerMedicoEmail(String email)
            throws ElementoNoEncontradoException;


    Medico buscarMedicoId(Long id) throws ElementoNoEncontradoException;

    // (CITA PARA PACIENTE)
    List<MedicoDto> listarMedicosEspecialidad(Long idEspecialidad);



    List<MedicoDto> listarMedicos(int pagina, int size,
                                  Long idEspecialidad);

}
