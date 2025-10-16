package co.edu.uniquindio.service.utils.impl;

import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.exceptions.ElementoRepetidoException;
import co.edu.uniquindio.models.tools.Ciudad;
import co.edu.uniquindio.models.users.Persona;
import co.edu.uniquindio.repository.users.AdminRepo;
import co.edu.uniquindio.repository.users.MedicoRepo;
import co.edu.uniquindio.repository.users.PacienteRepo;
import co.edu.uniquindio.repository.utils.CiudadRepo;
import co.edu.uniquindio.repository.utils.TelefonoRepo;
import co.edu.uniquindio.service.utils.PersonaUtilsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonaUtilServiceImpl implements PersonaUtilsService{


    private final PacienteRepo pacienteRepo;
    private final MedicoRepo medicoRepo;
    private final AdminRepo adminRepo;
    private final TelefonoRepo telefonoRepo;
    private final CiudadRepo ciudadRepo;


    @Override
    public void  validarEmailNoRepetido(String email) throws ElementoRepetidoException {

        if (pacienteRepo.existsByUser_Email(email)
                || medicoRepo.existsByUser_Email(email) || adminRepo.existsByUser_Email(email)) {
            throw new ElementoRepetidoException("Email ya utilizado por otra cuenta");
        }
    }


    @Override
    public void validarTelefonoNoRepetido(String telefono) throws ElementoRepetidoException {

        if (telefonoRepo.existsByNumero(telefono)) {
            throw new ElementoRepetidoException("Teléfono ya registrado en otra cuenta");
        }
    }


    @Override
    public Ciudad buscarCiudadId(Long id) throws ElementoNoEncontradoException {
        return ciudadRepo.findById(id)
                .orElseThrow(() -> new ElementoNoEncontradoException("Ciudad no registrada en el sistema"));
    }


    @Override
    public Persona buscarPersonaEmail(String email) throws ElementoNoEncontradoException {
        return
                pacienteRepo.findByUser_Email(email).map(paciente -> (Persona) paciente)
                .or(()-> medicoRepo.findByUser_Email(email).map(medico -> (Persona) medico)
                        .or(() -> adminRepo.findByUser_Email(email).map(admin -> (Persona) admin)))
                        .orElseThrow(() -> new ElementoNoEncontradoException("Persona no encontrado"));
    }
}

