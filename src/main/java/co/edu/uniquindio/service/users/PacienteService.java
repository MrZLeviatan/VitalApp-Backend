package co.edu.uniquindio.service.users;


import co.edu.uniquindio.dto.paciente.EditarPacienteDto;
import co.edu.uniquindio.dto.paciente.EliminarPacienteDto;
import co.edu.uniquindio.dto.paciente.PacienteDto;
import co.edu.uniquindio.dto.paciente.RegistrarPacienteDto;
import co.edu.uniquindio.exceptions.ElementoNoCoincideException;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.exceptions.ElementoRepetidoException;
import co.edu.uniquindio.models.objects.Formula;
import co.edu.uniquindio.models.users.Paciente;

import java.util.List;

public interface PacienteService {

    // Registro de Paciente Nuevo (USO DEL ADMIN)
    void registrarPaciente(RegistrarPacienteDto registrarPacienteDto)
            throws ElementoRepetidoException, ElementoNoEncontradoException;

    // Editar perfil de paciente (USO DE PACIENTE)
    void editarPaciente(EditarPacienteDto editarPacienteDto)
            throws ElementoNoEncontradoException;

    // Dar de estado 'Eliminado' al paciente (USO DE PACIENTE)
    void eliminarPaciente(EliminarPacienteDto eliminarPacienteDto)
            throws ElementoNoEncontradoException, ElementoNoCoincideException;

    // Obtener Paciente mediante su ID (USO DE PACIENTE Y ADMIN)
    PacienteDto obtenerPacienteId(Long id)
            throws ElementoNoEncontradoException;

    // Obtener Paciente mediante su email (USO DE PACIENTE Y ADMIN)
    PacienteDto obtenerPacienteEmail(String email)
            throws ElementoNoEncontradoException;

    // Obtener Paciente sin Dto mediante su Id
    Paciente buscarPacienteId(Long id)
            throws ElementoNoEncontradoException;

    // Listar Pacientes dependiendo sus parámetros (USO DE ADMIN)
    List<PacienteDto> listarPacientes(int pagina, int size,
                                      Long idEps, Long idCiudad);

    // SISTEMA
    void agregarFormulaPaciente(Paciente paciente, Formula formula);



}
