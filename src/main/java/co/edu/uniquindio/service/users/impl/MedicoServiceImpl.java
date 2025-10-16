package co.edu.uniquindio.service.users.impl;

import co.edu.uniquindio.dto.Telefono.RegistroTelefonoDto;
import co.edu.uniquindio.dto.medico.EditarMedicoDto;
import co.edu.uniquindio.dto.medico.EliminarMedicoDto;
import co.edu.uniquindio.dto.medico.MedicoDto;
import co.edu.uniquindio.dto.medico.RegistrarMedicoDto;
import co.edu.uniquindio.exceptions.ElementoNoCoincideException;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.exceptions.ElementoRepetidoException;
import co.edu.uniquindio.mapper.users.MedicoMapper;
import co.edu.uniquindio.models.enums.EstadoUser;
import co.edu.uniquindio.models.tools.Especialidad;
import co.edu.uniquindio.models.tools.Telefono;
import co.edu.uniquindio.models.users.Medico;
import co.edu.uniquindio.repository.users.MedicoRepo;
import co.edu.uniquindio.repository.utils.TelefonoRepo;
import co.edu.uniquindio.service.objects.AgendaService;
import co.edu.uniquindio.service.users.MedicoService;
import co.edu.uniquindio.service.utils.EspecialidadService;
import co.edu.uniquindio.service.utils.PersonaUtilsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MedicoServiceImpl implements MedicoService {


    private final PersonaUtilsService personaUtilsService;
    private final MedicoMapper medicoMapper;
    private final MedicoRepo medicoRepo;
    private final AgendaService agendaService;
    private final EspecialidadService especialidadService;
    private final TelefonoRepo telefonoRepo;



    @Override
    public void registrarMedico(RegistrarMedicoDto registrarMedicoDto)
            throws ElementoRepetidoException, ElementoNoEncontradoException {

        // 1. Validamos la no existencia del correo y el/los teléfonos
        personaUtilsService.validarEmailNoRepetido(registrarMedicoDto.user().email());

        // Validamos cada número telefónico del registro
        if (registrarMedicoDto.telefonos() != null && !registrarMedicoDto.telefonos().isEmpty() ) {

            for (RegistroTelefonoDto telefonosDto : registrarMedicoDto.telefonos()){
                // Normalizamos el número y lo validamos individualmente
                personaUtilsService.validarTelefonoNoRepetido(telefonosDto.numero());
            }
        }

        // 2. Mapeamos al Médico
        Medico medico = medicoMapper.toDto(registrarMedicoDto);

        // 3, Asociamos la especialidad del médico
        Especialidad especialidad = especialidadService.obtenerEspecialidad(registrarMedicoDto.idEspecialidad());
        medico.setEspecialidad(especialidad);


        // Asociar los teléfonos (sin reemplazar la lista)
        if (registrarMedicoDto.telefonos() != null) {
            for (RegistroTelefonoDto telefonoDto : registrarMedicoDto.telefonos()) {
                Telefono telefono = new Telefono();
                telefono.setNumero(telefonoDto.numero());
                medico.agregarTelefono(telefono);
            }
        }

        // Guardar médico (cascada guarda los teléfonos)
        medicoRepo.save(medico);

        // Se agrega a la lista de especialidades
        especialidadService.agregarMedicoEspecialidad(especialidad, medico);

        // Se le asigna una agenda mensual al Médico
        agendaService.generarAgendaMensual(medico);

    }


    @Override
    public void actualizarMedico(EditarMedicoDto editarMedicoDto)
            throws ElementoNoEncontradoException, ElementoNoCoincideException {

        // Buscamos al médico mediante su Id
        Medico medico = buscarMedicoId(editarMedicoDto.id());

        // Verificamos si la contraseña coincide
        if (!medico.getUser().getPassword().equals(editarMedicoDto.password())) {
            throw new ElementoNoCoincideException("El password no coincide");
        };

        // Editamos al médico mediante el mapeo
        medicoMapper.updateEntity(editarMedicoDto, medico);

        // Actualizamos la BD
        medicoRepo.save(medico);
    }


    @Override
    public void eliminarMedico(EliminarMedicoDto eliminarMedicoDto) throws ElementoNoEncontradoException {

        // Buscamos al médico mediante su Id
        Medico medico = buscarMedicoId(eliminarMedicoDto.idMedico());

        // Se cambia el estado del user del médico
        medico.getUser().setEstadoUser(EstadoUser.ELIMINADO);

        // Se guarda el estado en la BD
        medicoRepo.save(medico);
    }


    @Override
    public MedicoDto obtenerMedicoId(Long id) throws ElementoNoEncontradoException {
        return medicoMapper.toDto(buscarMedicoId(id));
    }

    @Override
    public MedicoDto obtenerMedicoEmail(String email) throws ElementoNoEncontradoException {
        return medicoMapper.toDto(medicoRepo.findByUser_Email(email)
                .orElseThrow(()-> new ElementoNoEncontradoException("Medico no registrado en el sistema.")));
    }


    @Override
    public Medico buscarMedicoId(Long id) throws ElementoNoEncontradoException {
        return medicoRepo.findById(id)
                .orElseThrow(()-> new ElementoNoEncontradoException("Médico no registrado en el sistema."));
    }


    @Override
    public List<MedicoDto> listarMedicosEspecialidad(Long idEspecialidad) {
        return medicoRepo.findAllByEspecialidad_Id(idEspecialidad)
                .stream()
                .map(medicoMapper::toDto)
                .collect(Collectors.toList());
    }



    @Override
    public List<MedicoDto> listarMedicos(int pagina, int size, Long idEspecialidad) {

        // 1. Construir un Pageable para paginación
        Pageable pageable = PageRequest.of(pagina, size);

        // 2. Crear predicado dinámico con filtros opcionales
        Specification<Medico> spec = Specification.where(null);

        // Sí se proporciona un ID de Especialidad, filtrar por Especialidad de los medicos
        if (idEspecialidad != null) {
            spec = spec.and(((root, query, builder) ->
                    builder.equal(root.get("especialidad").get("id"),idEspecialidad)));
        }

        // Obtener la lista página de pacientes filtrada
        Page<Medico> medicoPage = medicoRepo.findAll(spec, pageable);

        //Convertir a DTO's y filtrar
        return medicoPage.getContent().stream()
                .map(medicoMapper::toDto)
                .collect(Collectors.toList());
    }


}
