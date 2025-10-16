package co.edu.uniquindio.controller.admin;


import co.edu.uniquindio.dto.MensajeDto;
import co.edu.uniquindio.dto.cita.CitaDto;
import co.edu.uniquindio.dto.detalleFormula.DetalleFormulaDto;
import co.edu.uniquindio.dto.formula.FormulaDto;
import co.edu.uniquindio.dto.paciente.PacienteDto;
import co.edu.uniquindio.dto.paciente.RegistrarPacienteDto;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.exceptions.ElementoRepetidoException;
import co.edu.uniquindio.service.objects.CitasService;
import co.edu.uniquindio.service.objects.FormulaService;
import co.edu.uniquindio.service.users.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminPacienteController {


    private final PacienteService pacienteService;
    private final CitasService citasService;
    private final FormulaService formulaService;



    // ------- Pacientes ----------


    // Registro de pacientes
    @PostMapping("/paciente/registro")
    public ResponseEntity<MensajeDto<String>> registrarPaciente(@RequestBody RegistrarPacienteDto registrarPacienteDto)
            throws ElementoRepetidoException, ElementoNoEncontradoException {

        // Se crea el paciente mediante el método del servicio
        pacienteService.registrarPaciente(registrarPacienteDto);
        // Se confirma la creación del paciente
        return ResponseEntity.ok().body(new MensajeDto<>(false,"Paciente registrado exitosamente"));
    }


    // Obtener el Paciente mediante su Id
    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<MensajeDto<PacienteDto>> obtenerPacientePorId(@PathVariable Long idPaciente)
            throws ElementoNoEncontradoException {

        // Se obtiene el PacienteDto mediante el método del servicio
        PacienteDto paciente = pacienteService.obtenerPacienteId(idPaciente);
        // Se envía al PacienteDto con la afirmación de proceso
        return ResponseEntity.ok().body(new MensajeDto<>(false, paciente));
    }


    // Obtener el Paciente mediante su email
    @GetMapping("/paciente/buscar-email")
    public ResponseEntity<MensajeDto<PacienteDto>> obtenerPacientePorEmail(@RequestParam String email)
            throws ElementoNoEncontradoException {

        // Se obtiene el PacienteDto mediante el método del servicio
        PacienteDto paciente = pacienteService.obtenerPacienteEmail(email);
        // Se envía al PacienteDto con la afirmación de proceso
        return ResponseEntity.ok().body(new MensajeDto<>(false, paciente));
    }


    // Listar a todos los pacientes dependiendo de criterios
    @GetMapping("/paciente/listar")
    public ResponseEntity<MensajeDto<List<PacienteDto>>> listarPacientes(
            // Se toma los parámetros que haya enviado
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long idEps,
            @RequestParam(required = false) Long idCiudad) {
        // Se lista los pacientes y se devuelven
        List<PacienteDto> pacientes = pacienteService.listarPacientes(pagina, size, idEps, idCiudad);
        return ResponseEntity.ok().body(new MensajeDto<>(false, pacientes));
    }


    // ------- Citas Paciente ----------

    // Obtener todas las citas de un paciente por su ID
    @GetMapping("/paciente/{idPaciente}/citas")
    public ResponseEntity<MensajeDto<List<CitaDto>>> obtenerCitasPorPaciente(@PathVariable Long idPaciente)
            throws ElementoNoEncontradoException {

        List<CitaDto> citas = citasService.obtenerCitasPaciente(idPaciente);
        return ResponseEntity.ok().body(new MensajeDto<>(false,citas));
    }


    // ------- Formulas y Detalles del paciente ----------

    // Obtiene las fórmulas de un paciente
    @GetMapping("/paciente/{idPaciente}/formula")
    public ResponseEntity<MensajeDto<List<FormulaDto>>> obtenerFormulasPaciente(@PathVariable Long idPaciente)
            throws ElementoNoEncontradoException {

        List<FormulaDto> listaFormulas = formulaService.obtenerFormulasPaciente(idPaciente);
        return ResponseEntity.ok().body(new MensajeDto<>(false, listaFormulas));
    }


    // Obtiene una formula especifica
    @GetMapping("/paciente/formula/{idFormula}")
    public ResponseEntity<MensajeDto<FormulaDto>> obtenerFormula(@PathVariable Long idFormula)
            throws ElementoNoEncontradoException {

        FormulaDto formulaDto = formulaService.obtenerFormulaDto(idFormula);
        return ResponseEntity.ok().body(new MensajeDto<>(false, formulaDto));
    }


    // Obtiene los detalles de una fórmula
    @GetMapping("/paciente/formula/{idFormula}/detalles")
    public ResponseEntity<MensajeDto<List<DetalleFormulaDto>>> obtenerDetallesFormula(@PathVariable Long idFormula)
            throws ElementoNoEncontradoException {

        List<DetalleFormulaDto> detalleFormulaDtos = formulaService.obtenerDetalleFormula(idFormula);
        return ResponseEntity.ok().body(new MensajeDto<>(false, detalleFormulaDtos));
    }

}
