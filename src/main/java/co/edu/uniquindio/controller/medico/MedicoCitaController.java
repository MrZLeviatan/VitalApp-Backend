package co.edu.uniquindio.controller.medico;


import co.edu.uniquindio.dto.MensajeDto;
import co.edu.uniquindio.dto.cita.CambiarEstadoCitaDto;
import co.edu.uniquindio.dto.cita.CitaDto;
import co.edu.uniquindio.dto.formula.RegistroFormulaDto;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.service.objects.CitasService;
import co.edu.uniquindio.service.objects.FormulaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medico")
public class MedicoCitaController {

    private final CitasService citasService;
    private final FormulaService formulaService;

    // Obtener todas las citas de un médico por su Id
    @GetMapping("/{idMedico}/citas")
    public ResponseEntity<MensajeDto<List<CitaDto>>> obtenerCitasMedico(@PathVariable Long idMedico)
            throws ElementoNoEncontradoException {

        List<CitaDto> citas = citasService.obtenerCitasMedico(idMedico);
        return ResponseEntity.ok().body(new MensajeDto<>(false,citas));
    }


    // Obtener una cita por su ID
    @GetMapping("/citas/{idCita}")
    public ResponseEntity<MensajeDto<CitaDto>> obtenerCitaPorId(@PathVariable Long idCita)
            throws ElementoNoEncontradoException {

        CitaDto citaDto = citasService.obtenerCitaDtoId(idCita);
        return ResponseEntity.ok().body(new MensajeDto<>(false,citaDto));
    }


    // poner EN_REVISION una cita
    @PutMapping("/citas/revision")
    public ResponseEntity<MensajeDto<String>> revisionCita(@RequestBody CambiarEstadoCitaDto dto)
            throws ElementoNoEncontradoException {
        citasService.cambiarEstadoCita(dto);
        return ResponseEntity.ok().body(new MensajeDto<>(false,"Cita puesta en revision correctamente"));
    }


    // --------- Registro formula de la cita

    @PostMapping("/cita/formula/registro")
    public ResponseEntity<MensajeDto<String>> registrarFormula(@RequestBody RegistroFormulaDto registroFormulaDto)
            throws ElementoNoEncontradoException {
        // Registro de fórmula en el servicio
        formulaService.registrarFormula(registroFormulaDto);
        return ResponseEntity.ok().body(new MensajeDto<>(false,"Formula registrada"));
    }




}
