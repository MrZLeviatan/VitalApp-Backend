package co.edu.uniquindio.service.users.impl;

import co.edu.uniquindio.dto.user.CambiarContraseniaDto;
import co.edu.uniquindio.dto.user.EditarUserDto;
import co.edu.uniquindio.exceptions.ElementoNoCoincideException;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.exceptions.ElementoRepetidoException;
import co.edu.uniquindio.models.users.Medico;
import co.edu.uniquindio.models.users.Paciente;
import co.edu.uniquindio.repository.users.MedicoRepo;
import co.edu.uniquindio.repository.users.PacienteRepo;
import co.edu.uniquindio.service.users.UserService;
import co.edu.uniquindio.service.utils.PersonaUtilsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final PacienteRepo pacienteRepo;
    private final MedicoRepo medicoRepo;
    private final PersonaUtilsService personaUtilsService;


    // Método para editar el email de los usuarios
    @Override
    public void editarEmail(EditarUserDto editarUserDto)
            throws ElementoRepetidoException, ElementoNoEncontradoException, ElementoNoCoincideException {

        // Valida que el nuevo email no este repetido
        personaUtilsService.validarEmailNoRepetido(editarUserDto.emailNuevo());

        // Busca a cuál paciente / médico pertenece el ID
        if (pacienteRepo.findById(editarUserDto.id()).isPresent()) {
            // Obtiene el paciente mediante el Repo
            Paciente paciente = pacienteRepo.findById(editarUserDto.id()).get();
            // Válida si el password coincide
            if (!paciente.getUser().getPassword().equals(editarUserDto.password())) {
                throw new ElementoNoCoincideException("El password no coincide");
            }
            // Actualiza el email del usuario
            paciente.getUser().setEmail(editarUserDto.emailNuevo());
            // Guarda los cambios
            pacienteRepo.save(paciente);
        // Si no es paciente, se busca si es médico
        }else if (medicoRepo.findById(editarUserDto.id()).isPresent()) {
            // Obtiene el médico mediante el Repo
            Medico medico = medicoRepo.findById(editarUserDto.id()).get();
            // Válida si el password coincide
            if (!medico.getUser().getPassword().equals(editarUserDto.password())) {
                throw new ElementoNoCoincideException("El password no coincide");
            }
            // Actualiza el email del médico
            medico.getUser().setEmail(editarUserDto.emailNuevo());
            // Guarda los cambios
            medicoRepo.save(medico);
        }else{
            throw new ElementoNoEncontradoException("No se encontró ningún usuario con ese Id");
        }
    }


    @Override
    public void cambiarPassword(CambiarContraseniaDto cambiarContraseniaDto)
            throws ElementoNoCoincideException, ElementoNoEncontradoException {

        // Busca a cuál paciente / médico pertenece el ID
        if (pacienteRepo.findById(cambiarContraseniaDto.id()).isPresent()) {
            // Obtiene el paciente mediante el Repo
            Paciente paciente = pacienteRepo.findById(cambiarContraseniaDto.id()).get();
            // Válida si el password coincide
            if (!paciente.getUser().getPassword().equals(cambiarContraseniaDto.antiguoPassword())) {
                throw new ElementoNoCoincideException("El antiguo password no coincide");
            }
            // Actualiza el password del usuario
            paciente.getUser().setPassword(cambiarContraseniaDto.nuevoPassword());
            // Guarda los cambios
            pacienteRepo.save(paciente);
            // Si no es paciente, se busca si es médico
        }else if (medicoRepo.findById(cambiarContraseniaDto.id()).isPresent()) {
            // Obtiene el médico mediante el Repo
            Medico medico = medicoRepo.findById(cambiarContraseniaDto.id()).get();
            // Válida si el password coincide
            if (!medico.getUser().getPassword().equals(cambiarContraseniaDto.antiguoPassword())) {
                throw new ElementoNoCoincideException("El antiguo password no coincide");
            }
            // Actualiza el password del médico
            medico.getUser().setPassword(cambiarContraseniaDto.nuevoPassword());
            // Guarda los cambios
            medicoRepo.save(medico);
        }else{
            throw new ElementoNoEncontradoException("No se encontró ningún usuario con ese Id");
        }
    }
}
