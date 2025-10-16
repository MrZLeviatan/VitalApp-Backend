package co.edu.uniquindio.controller.admin;

import co.edu.uniquindio.dto.MensajeDto;
import co.edu.uniquindio.dto.medicamento.MedicamentoDto;
import co.edu.uniquindio.dto.medicamento.RegistrarMedicamentoDto;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.service.objects.MedicamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminMedicamentoController {


    private final MedicamentoService medicamentoService;


    @PostMapping("/medicamento/registro")
    public ResponseEntity<MensajeDto<String>> registrarMedicamento(@RequestBody RegistrarMedicamentoDto registrarMedicamentoDto){

        medicamentoService.registrarMedicamento(registrarMedicamentoDto);
        return ResponseEntity.ok().body(new MensajeDto<>(false,"Medicamento registrado exitosamente"));
    }


    @GetMapping("/medicamento/{idMedicamento}")
    public ResponseEntity<MensajeDto<MedicamentoDto>> obtenerMedicamentoDto(@PathVariable Long idMedicamento)
            throws ElementoNoEncontradoException {

        MedicamentoDto medicamentoDto = medicamentoService.obtenerMedicamentoDtoId(idMedicamento);
        return ResponseEntity.ok().body(new MensajeDto<>(false ,medicamentoDto));
    }


    @GetMapping("medicamento/listar")
    public ResponseEntity<MensajeDto<List<MedicamentoDto>>> obtenerMedicamentos(){

        List<MedicamentoDto> listaMedicamento = medicamentoService.listarMedicamentos();
        return ResponseEntity.ok().body(new MensajeDto<>(false, listaMedicamento));
    }




}
