package co.edu.uniquindio.controller.paciente;

import co.edu.uniquindio.dto.MensajeDto;
import co.edu.uniquindio.dto.paciente.EditarPacienteDto;
import co.edu.uniquindio.dto.paciente.EliminarPacienteDto;
import co.edu.uniquindio.dto.paciente.PacienteDto;
import co.edu.uniquindio.dto.user.CambiarContraseniaDto;
import co.edu.uniquindio.dto.user.EditarUserDto;
import co.edu.uniquindio.exceptions.ElementoNoCoincideException;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.exceptions.ElementoRepetidoException;
import co.edu.uniquindio.service.users.PacienteService;
import co.edu.uniquindio.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/paciente")
public class PacientePerfilController {


    private final PacienteService pacienteService;
    private final UserService userService;


    // ------- Ver Perfil ----------

    // Obtener el Paciente mediante su Id
    @GetMapping("/{id}")
    public ResponseEntity<MensajeDto<PacienteDto>> obtenerPacientePorId(@PathVariable Long id)
            throws ElementoNoEncontradoException {

        // Se obtiene el PacienteDto mediante el método del servicio
        PacienteDto paciente = pacienteService.obtenerPacienteId(id);
        // Se envía al PacienteDto con la afirmación de proceso
        return ResponseEntity.ok().body(new MensajeDto<>(false, paciente));
    }


    // Obtener el Paciente mediante su email
    @GetMapping("/buscar-email")
    public ResponseEntity<MensajeDto<PacienteDto>> obtenerPacientePorEmail(@RequestParam String email)
            throws ElementoNoEncontradoException {

        // Se obtiene el PacienteDto mediante el método del servicio
        PacienteDto paciente = pacienteService.obtenerPacienteEmail(email);
        // Se envía al PacienteDto con la afirmación de proceso
        return ResponseEntity.ok().body(new MensajeDto<>(false, paciente));
    }


    // ------- Editar Perfil ----------

    // Editar perfil general
    @PutMapping("/editar-perfil")
    public ResponseEntity<MensajeDto<String>> editarPaciente(@RequestBody EditarPacienteDto editarPacienteDto)
            throws ElementoNoEncontradoException {

        // Llama al método del servicio
        pacienteService.editarPaciente(editarPacienteDto);
        // Se envía una respuesta de afirmación
        return ResponseEntity.ok().body(new MensajeDto<>(false, "Paciente editado correctamente"));
    }

    //Editar el email del Paciente
    @PutMapping("/editar-email")
    public ResponseEntity<MensajeDto<String>> editarEmailPaciente(@RequestBody EditarUserDto editarUserDto)
            throws ElementoRepetidoException, ElementoNoCoincideException, ElementoNoEncontradoException {

        // Se modifica el email mediante el método del servicio
        userService.editarEmail(editarUserDto);
        // Se envía una afirmación del proceso
        return ResponseEntity.ok().body(new MensajeDto<>(false, "Email editado correctamente"));
    }


    @PutMapping("/editar-password")
    public ResponseEntity<MensajeDto<String>> editarPasswordPaciente(@RequestBody CambiarContraseniaDto cambiarContraseniaDto)
            throws ElementoNoCoincideException, ElementoNoEncontradoException {

        // Se modifica el password mediante el método del servicio
        userService.cambiarPassword(cambiarContraseniaDto);
        // Se envía una afirmación del proceso
        return ResponseEntity.ok().body(new MensajeDto<>(false, "Contrasenia editado correctamente"));
    }


    // ------- Eliminar Perfil ----------

    // Eliminar perfil (cambiar estada cuenta)
    @DeleteMapping("/eliminar-perfil")
    public ResponseEntity<MensajeDto<String>> eliminarPaciente(@RequestBody EliminarPacienteDto eliminarPacienteDto)
            throws ElementoNoCoincideException, ElementoNoEncontradoException {

        // Llama al método del servicio
        pacienteService.eliminarPaciente(eliminarPacienteDto);
        // Se envía una respuesta de afirmación
        return ResponseEntity.ok().body(new MensajeDto<>(false, "Paciente eliminado exitosamente"));
    }
}
