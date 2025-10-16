package co.edu.uniquindio.controller.medico;


import co.edu.uniquindio.dto.MensajeDto;
import co.edu.uniquindio.dto.agenda.AgendaDto;
import co.edu.uniquindio.dto.medico.EditarMedicoDto;
import co.edu.uniquindio.dto.medico.MedicoDto;
import co.edu.uniquindio.dto.user.CambiarContraseniaDto;
import co.edu.uniquindio.dto.user.EditarUserDto;
import co.edu.uniquindio.exceptions.ElementoNoCoincideException;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.exceptions.ElementoRepetidoException;
import co.edu.uniquindio.service.objects.AgendaService;
import co.edu.uniquindio.service.users.MedicoService;
import co.edu.uniquindio.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medico")
public class MedicoPerfilController {


    private final MedicoService medicoService;
    private final UserService userService;
    private final AgendaService agendaService;


    // ------- Ver Perfil ----------

    // Obtener el Médico mediante su Id
    @GetMapping("/{id}")
    public ResponseEntity<MensajeDto<MedicoDto>> obtenerMedicoPorId(@PathVariable Long id)
            throws ElementoNoEncontradoException {

        // Se obtiene el MedicoDto mediante el método del servicio
        MedicoDto medico = medicoService.obtenerMedicoId(id);
        // Se envía al MeditoDto con la afirmación de proceso
        return ResponseEntity.ok().body(new MensajeDto<>(false, medico));
    }

    // Obtener el Médico mediante su email
    @GetMapping("/buscar-email")
    public ResponseEntity<MensajeDto<MedicoDto>> obtenerMedicoPorEmail(@RequestParam String email)
            throws ElementoNoEncontradoException {

        // Se obtiene el MedicoDto mediante el método del servicio
        MedicoDto medico = medicoService.obtenerMedicoEmail(email);
        // Se envía al MedicoDto con la afirmación de proceso
        return ResponseEntity.ok().body(new MensajeDto<>(false, medico));
    }


    // ------- Editar Perfil ----------

    // Editar perfil general
    @PutMapping("/editar-perfil")
    public ResponseEntity<MensajeDto<String>> editarMedico(@RequestBody EditarMedicoDto editarMedicoDto)
            throws ElementoNoEncontradoException, ElementoNoCoincideException {

        // Llama al método del servicio
        medicoService.actualizarMedico(editarMedicoDto);
        // Se envía una respuesta de afirmación
        return ResponseEntity.ok().body(new MensajeDto<>(false, "Medico editado correctamente"));
    }


    //Editar el email del Médico
    @PutMapping("/editar-email")
    public ResponseEntity<MensajeDto<String>> editarEmailMedico(@RequestBody EditarUserDto editarUserDto)
            throws ElementoRepetidoException, ElementoNoCoincideException, ElementoNoEncontradoException {

        // Se modifica el email mediante el método del servicio
        userService.editarEmail(editarUserDto);
        // Se envía una afirmación del proceso
        return ResponseEntity.ok().body(new MensajeDto<>(false, "Email editado correctamente"));
    }


    @PutMapping("/editar-password")
    public ResponseEntity<MensajeDto<String>> editarPasswordMedico(@RequestBody CambiarContraseniaDto cambiarContraseniaDto)
            throws ElementoNoCoincideException, ElementoNoEncontradoException {

        // Se modifica el password mediante el método del servicio
        userService.cambiarPassword(cambiarContraseniaDto);
        // Se envía una afirmación del proceso
        return ResponseEntity.ok().body(new MensajeDto<>(false, "Contraseña editado correctamente"));
    }


    // ------- Agendas ----------

    // Ver agenda del médico seleccionado
    @GetMapping("/{idMedico}/agenda")
    public ResponseEntity<MensajeDto<List<AgendaDto>>> listarAgendaMedico(@PathVariable Long idMedico)
            throws ElementoNoEncontradoException {

        List<AgendaDto> listarAgenda = agendaService.listarAgendaMedicoId(idMedico);
        return ResponseEntity.ok().body(new MensajeDto<>(false, listarAgenda));
    }



}
