package co.edu.uniquindio.controller.paciente;

import co.edu.uniquindio.dto.MensajeDto;
import co.edu.uniquindio.dto.agenda.AgendaDto;
import co.edu.uniquindio.dto.cita.CambiarEstadoCitaDto;
import co.edu.uniquindio.dto.cita.CitaDto;
import co.edu.uniquindio.dto.cita.RegistrarCitaDto;
import co.edu.uniquindio.dto.especialidad.EspecialidadDto;
import co.edu.uniquindio.dto.medico.MedicoDto;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.service.objects.AgendaService;
import co.edu.uniquindio.service.objects.CitasService;
import co.edu.uniquindio.service.users.MedicoService;
import co.edu.uniquindio.service.utils.EspecialidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/paciente")
public class PacienteCitaController {


    private final CitasService citasService;
    private final EspecialidadService especialidadService;
    private final MedicoService medicoService;
    private final AgendaService agendaService;


    // ------- CITAS ----------

    // Registrar una cita médica
    @PostMapping("/citas/registro")
    public ResponseEntity<MensajeDto<String>> registrarCita(@RequestBody RegistrarCitaDto registrarCitaDto)
            throws ElementoNoEncontradoException {

        citasService.registraCita(registrarCitaDto);

        return ResponseEntity.ok().body(new MensajeDto<>(false,"Cita agendada correctamente"));
    }

    // Obtener todas las citas Pendientes de un paciente por su ID
    @GetMapping("/{idPaciente}/citas/pendientes")
    public ResponseEntity<List<CitaDto>> listarCitasPendientesPaciente(@PathVariable Long idPaciente)
            throws ElementoNoEncontradoException {
        return ResponseEntity.ok(citasService.obtenerCitasPacientePendientes(idPaciente));
    }

    // Obtener todas las citas de un paciente por su ID
    @GetMapping("/{idPaciente}/citas")
    public ResponseEntity<List<CitaDto>> obtenerCitasPorPaciente(@PathVariable Long idPaciente)
            throws ElementoNoEncontradoException {

        List<CitaDto> citas = citasService.obtenerCitasPaciente(idPaciente);
        return ResponseEntity.ok(citas);
    }


    // Obtener una cita por su ID
    @GetMapping("/citas/{idCita}")
    public ResponseEntity<CitaDto> obtenerCitaPorId(@PathVariable Long idCita)
            throws ElementoNoEncontradoException {

        CitaDto citaDto = citasService.obtenerCitaDtoId(idCita);
        return ResponseEntity.ok(citaDto);
    }


    // Cancelar una cita médica
    @PutMapping("/citas/cancelar")
    public ResponseEntity<MensajeDto<String>> cancelarCita(@RequestBody CambiarEstadoCitaDto dto)
            throws ElementoNoEncontradoException {
        citasService.cancelarCita(dto);
        return ResponseEntity.ok().body(new MensajeDto<>(false,"Cita cancelada correctamente"));
    }


    // ------- ESPECIALIZACIÓN ----------

    // Listar todas las especialidades
    @GetMapping("/citas/registro/especialidades")
    public ResponseEntity<MensajeDto<List<EspecialidadDto>>> listarEspecialidades() {

        List<EspecialidadDto> especialidades = especialidadService.obtenerEspecialidades();
        return ResponseEntity.ok().body(new MensajeDto<>(false, especialidades));
    }


    // ------- MÉDICOS ----------

    // Ver Medicos de la especialidad seleccionada
    @GetMapping("/citas/registro/especialidades/{idEspecializacion}/medicos")
    public ResponseEntity<MensajeDto<List<MedicoDto>>> listarMedicosSegunEspecializacion(@PathVariable Long idEspecializacion) {

        List<MedicoDto> listaMedicos = medicoService.listarMedicosEspecialidad(idEspecializacion);
        return ResponseEntity.ok().body(new MensajeDto<>(false, listaMedicos));
    }

    // Ver agenda libre del médico seleccionado
    @GetMapping("/citas/medicos/{idMedico}/agenda")
    public ResponseEntity<MensajeDto<List<AgendaDto>>> listarAgendaMedico(@PathVariable Long idMedico)
            throws ElementoNoEncontradoException {

        List<AgendaDto> listarAgenda = agendaService.listarAgendaLibreMedico(idMedico);
        return ResponseEntity.ok().body(new MensajeDto<>(false, listarAgenda));
    }

}
