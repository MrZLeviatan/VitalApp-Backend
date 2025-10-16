package co.edu.uniquindio.service.users.impl;

import co.edu.uniquindio.dto.Telefono.RegistroTelefonoDto;
import co.edu.uniquindio.dto.paciente.EditarPacienteDto;
import co.edu.uniquindio.dto.paciente.EliminarPacienteDto;
import co.edu.uniquindio.dto.paciente.PacienteDto;
import co.edu.uniquindio.dto.paciente.RegistrarPacienteDto;
import co.edu.uniquindio.exceptions.ElementoNoCoincideException;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.exceptions.ElementoRepetidoException;
import co.edu.uniquindio.mapper.users.PacienteMapper;
import co.edu.uniquindio.models.enums.EstadoUser;
import co.edu.uniquindio.models.objects.Eps;
import co.edu.uniquindio.models.objects.Formula;
import co.edu.uniquindio.models.tools.Telefono;
import co.edu.uniquindio.models.users.Paciente;
import co.edu.uniquindio.repository.users.PacienteRepo;
import co.edu.uniquindio.service.users.PacienteService;
import co.edu.uniquindio.service.utils.EpsService;
import co.edu.uniquindio.service.utils.PersonaUtilsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {


    private final PacienteMapper pacienteMapper;
    private final PacienteRepo pacienteRepo;
    private final PersonaUtilsService personaUtilsService;
    private final EpsService epsService;



    @Override
    public void registrarPaciente(RegistrarPacienteDto registrarPacienteDto)
            throws ElementoRepetidoException, ElementoNoEncontradoException {

        // 1. Validamos la no existencia del correo y el o los teléfonos
        personaUtilsService.validarEmailNoRepetido(registrarPacienteDto.crearUserDto().email());

        // Validamos cada número telefónico del registro
        if (registrarPacienteDto.telefonos() != null && !registrarPacienteDto.telefonos().isEmpty() ) {

            for (RegistroTelefonoDto telefonosDto : registrarPacienteDto.telefonos()){
                // Normalizamos el número y lo validamos individualmente
                personaUtilsService.validarTelefonoNoRepetido(telefonosDto.numero());
            }
        }

        // 2. Mapeamos el Paciente
        Paciente paciente = pacienteMapper.toEntity(registrarPacienteDto);

        // 3. Asociamos la Eps y la Ciudad al Paciente mediante los ID's

        Eps eps = epsService.encontrarEps(registrarPacienteDto.idEps());
        paciente.setEps(eps);

        paciente.setCiudad(personaUtilsService.buscarCiudadId(registrarPacienteDto.idCiudad()));

        // Asociar los teléfonos (sin reemplazar la lista)
        if (registrarPacienteDto.telefonos() != null) {
            for (RegistroTelefonoDto telefonoDto : registrarPacienteDto.telefonos()) {
                Telefono telefono = new Telefono();
                telefono.setNumero(telefonoDto.numero());
                paciente.agregarTelefono(telefono);
            }
        }

        // 4. Guardamos en el sistema
        pacienteRepo.save(paciente);

        // 5. Asociamos al paciente con la eps
        epsService.agregarPacienteEps(eps,paciente);

    }


    @Override
    public void editarPaciente(EditarPacienteDto editarPacienteDto) throws ElementoNoEncontradoException {

        // Buscamos la persona mediante su Id
        Paciente paciente = buscarPacienteId(editarPacienteDto.id());

        // Editamos la persona mediante el mapeo
        pacienteMapper.updateEntity(editarPacienteDto, paciente);

        // Verificamos si cambio de ciudad y la actualizamos
        if (editarPacienteDto.idCiudad() != null) {
            paciente.setCiudad(personaUtilsService.buscarCiudadId(editarPacienteDto.idCiudad()));
        }
        // Actualizamos la base de datos
        pacienteRepo.save(paciente);
    }



    @Override
    public void eliminarPaciente(EliminarPacienteDto eliminarPacienteDto)
            throws ElementoNoEncontradoException, ElementoNoCoincideException {

        // Buscamos la persona mediante su ID
        Paciente paciente = buscarPacienteId(eliminarPacienteDto.idPaciente());

        // Verificamos si su contraseña coincide
        if (!paciente.getUser().getPassword().equals(eliminarPacienteDto.password())) {
            throw new ElementoNoCoincideException("El password no coincide");
        };

        // Se cambia el estado del user del paciente
        paciente.getUser().setEstadoUser(EstadoUser.ELIMINADO);

        pacienteRepo.save(paciente);
    }


    @Override
    public PacienteDto obtenerPacienteId(Long id) throws ElementoNoEncontradoException {
        return pacienteMapper.toDto(buscarPacienteId(id));
    }

    @Override
    public PacienteDto obtenerPacienteEmail(String email) throws ElementoNoEncontradoException {
        return pacienteMapper.toDto(buscarPacienteEmail(email));
    }

    @Override
    public Paciente buscarPacienteId (Long id) throws ElementoNoEncontradoException {
        return pacienteRepo.findById(id)
                .orElseThrow(()-> new ElementoNoEncontradoException("Paciente no registrado en el sistema."));
    }


    private Paciente buscarPacienteEmail(String email) throws ElementoNoEncontradoException {
        return pacienteRepo.findByUser_Email(email)
                .orElseThrow(()-> new ElementoNoEncontradoException("Paciente no registrado en el sistema."));
    }


    @Override
    public List<PacienteDto> listarPacientes(int pagina, int size, Long idEps, Long idCiudad) {

        // 1. Construir un Pageable para paginación
        Pageable pageable = PageRequest.of(pagina, size);

        // 2. Crear predicado dinámico con filtros opcionales
        Specification<Paciente> spec = Specification.where(null);

        // Sí se proporciona un ID de Eps, filtrar por Eps de los pacientes
        if (idEps != null) {
            spec = spec.and(((root, query, builder) ->
                    builder.equal(root.get("eps").get("id"),idEps)));
        }

        // Sí se proporciona un ID de Ciudad, filtrar por Ciudad los pacientes
        if (idCiudad != null) {
            spec = spec.and(((root, query, builder) ->
                    builder.equal(root.get("ciudad").get("id"),idCiudad)));
        }

        // Obtener la lista página de pacientes filtrada
        Page<Paciente> pacientePage = pacienteRepo.findAll(spec, pageable);

        // Convertir a DTO's y filtrar
        return pacientePage.getContent().stream()
                .map(pacienteMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public void agregarFormulaPaciente(Paciente paciente, Formula formula) {
        paciente.getFormulasMedicas().add(formula);
        pacienteRepo.save(paciente);
    }


}
