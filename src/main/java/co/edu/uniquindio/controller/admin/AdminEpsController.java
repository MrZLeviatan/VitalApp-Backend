package co.edu.uniquindio.controller.admin;


import co.edu.uniquindio.dto.MensajeDto;
import co.edu.uniquindio.dto.eps.EpsDto;
import co.edu.uniquindio.dto.eps.RegistrarEpsDto;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.service.utils.EpsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminEpsController {


    private final EpsService epsService;


    // Método para registrar eps al sistema
    @PostMapping("/eps/registrar")
    public ResponseEntity<MensajeDto<String>> registrarEps (@RequestBody RegistrarEpsDto registrarEpsDto){

        epsService.registrarEps(registrarEpsDto);
        return ResponseEntity.ok().body(new MensajeDto<>(false, "Eps registrada exitosamente"));
    }

    // Obtiene Eps mediante su ID
    @GetMapping("/eps/{id}")
    public ResponseEntity<MensajeDto<EpsDto>> obtenerEpsId(@PathVariable Long id)
            throws ElementoNoEncontradoException {

        EpsDto epsDto = epsService.obtenerEpsDto(id);
        return ResponseEntity.ok().body(new MensajeDto<>(false , epsDto));
    }

    // Obtener la lista de las eps registradas en el sistema
    @GetMapping("/eps/listar")
    public ResponseEntity<MensajeDto<List<EpsDto>>> obtenerEpsDto() {

        List<EpsDto> listaEps = epsService.listarEpsDto();
        return ResponseEntity.ok().body(new MensajeDto<>(false , listaEps));
    }
}
