package co.edu.uniquindio.controller.admin;

import co.edu.uniquindio.dto.MensajeDto;
import co.edu.uniquindio.dto.especialidad.AgregarEspecialidadDto;
import co.edu.uniquindio.dto.especialidad.EspecialidadDto;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.service.utils.EspecialidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminEspecialidadController {

    private final EspecialidadService especialidadService;


    // Registro de especialidad
    @PostMapping("/especialidad/registro")
    public ResponseEntity<MensajeDto<String>> registrarEspecialidad(@RequestBody AgregarEspecialidadDto agregarEspecialidadDto){

        especialidadService.registrarEspecialidad(agregarEspecialidadDto);
        return ResponseEntity.ok().body(new MensajeDto<>(false, "Especialidad registrado exitosamente"));
    }

    // Obtener una especialidad mediante su Id
    @GetMapping("/especialidad/{idEspecialidad}")
    public ResponseEntity<MensajeDto<EspecialidadDto>> obtenerEspecialidadId(@PathVariable Long idEspecialidad)
            throws ElementoNoEncontradoException {

        EspecialidadDto especialidadDto = especialidadService.obtenerEspecialidadDto(idEspecialidad);
        return ResponseEntity.ok().body(new MensajeDto<>(false, especialidadDto));
    }

    // Listar todas las especialidades
    @GetMapping("/especialidad/listar")
    public ResponseEntity<MensajeDto<List<EspecialidadDto>>> listarEspecialidades() {

        List<EspecialidadDto> especialidades = especialidadService.obtenerEspecialidades();
        return ResponseEntity.ok().body(new MensajeDto<>(false, especialidades));
    }

}
