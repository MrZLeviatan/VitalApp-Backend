package co.edu.uniquindio.controller.admin;

import co.edu.uniquindio.dto.MensajeDto;
import co.edu.uniquindio.dto.agenda.AgendaDto;
import co.edu.uniquindio.dto.cita.CitaDto;
import co.edu.uniquindio.dto.medico.EliminarMedicoDto;
import co.edu.uniquindio.dto.medico.MedicoDto;
import co.edu.uniquindio.dto.medico.RegistrarMedicoDto;

import co.edu.uniquindio.exceptions.ElementoNoCoincideException;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.exceptions.ElementoRepetidoException;
import co.edu.uniquindio.service.objects.AgendaService;
import co.edu.uniquindio.service.objects.CitasService;
import co.edu.uniquindio.service.users.MedicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminMedicoController {


    private final MedicoService medicoService;
    private final CitasService citasService;
    private final AgendaService agendaService;


    // Registro de medicos
    @PostMapping("/medico/registro")
    public ResponseEntity<MensajeDto<String>> registrarMedicos(@RequestBody RegistrarMedicoDto registrarMedicoDto)
            throws ElementoRepetidoException, ElementoNoEncontradoException {

        // Se crea el medico mediante el método del servicio
        medicoService.registrarMedico(registrarMedicoDto);
        // Se confirma la creación del medico
        return ResponseEntity.ok().body(new MensajeDto<>(false,"Paciente registrado exitosamente"));
    }


    //Obtener medico mediante su Id
    @GetMapping("/medico/{idMedico}")
    public ResponseEntity<MensajeDto<MedicoDto>> obtenerMedicoPorId(@PathVariable Long idMedico)
            throws ElementoNoEncontradoException {

        // Se obtiene el MedicoDto mediante el método del servicio
        MedicoDto medicoDto = medicoService.obtenerMedicoId(idMedico);
        // Se envía el MedicoDto con la afirmación del proceso
        return ResponseEntity.ok().body(new MensajeDto<>(false, medicoDto));
    }


    //Obtener medico mediante su Email
    @GetMapping("/medico/buscar-email")
    public ResponseEntity<MensajeDto<MedicoDto>> obtenerMedicoPorEmail(@RequestParam String email)
            throws ElementoNoEncontradoException {

        // Se obtiene el MedicoDto mediante el método del servicio
        MedicoDto medicoDto = medicoService.obtenerMedicoEmail(email);
        // Se envía el MedicoDto con la afirmación del proceso
        return ResponseEntity.ok().body(new MensajeDto<>(false, medicoDto));
    }


    // Eliminar perfil (cambiar estada cuenta)
    @DeleteMapping("/medico/eliminar-perfil")
    public ResponseEntity<MensajeDto<String>> eliminarPaciente(@RequestBody EliminarMedicoDto eliminarMedicoDto)
            throws ElementoNoCoincideException, ElementoNoEncontradoException {

        // Llama al método del servicio
        medicoService.eliminarMedico(eliminarMedicoDto);
        // Se envía una respuesta de afirmación
        return ResponseEntity.ok().body(new MensajeDto<>(false, "Medico eliminado exitosamente"));
    }


    // Listar a todos los medicos dependiendo de criterios
    @GetMapping("/medico/listar")
    public ResponseEntity<MensajeDto<List<MedicoDto>>> listarMedicos(
            // Se toma los parámetros que haya enviado
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long idEspecialidad) {
        // Se lista los medicos y se devuelven
        List<MedicoDto> medicos = medicoService.listarMedicos(pagina, size,idEspecialidad);
        return ResponseEntity.ok().body(new MensajeDto<>(false, medicos));
    }


    // ------- Agendas ----------

    // Ver agenda del médico seleccionado
    @GetMapping("/medicos/{idMedico}/agenda")
    public ResponseEntity<MensajeDto<List<AgendaDto>>> listarAgendaMedico(@PathVariable Long idMedico)
            throws ElementoNoEncontradoException {

        List<AgendaDto> listarAgenda = agendaService.listarAgendaMedicoId(idMedico);
        return ResponseEntity.ok().body(new MensajeDto<>(false, listarAgenda));
    }


    // ------- Citas ----------

    // Obtener todas las citas de un médico por su Id
    @GetMapping("/medico/{idMedico}/citas")
    public ResponseEntity<MensajeDto<List<CitaDto>>> obtenerCitasMedico(@PathVariable Long idMedico)
            throws ElementoNoEncontradoException {

        List<CitaDto> citas = citasService.obtenerCitasMedico(idMedico);
        return ResponseEntity.ok().body(new MensajeDto<>(false,citas));
    }


}