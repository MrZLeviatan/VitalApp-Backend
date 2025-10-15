package co.edu.uniquindio.controller;

import co.edu.uniquindio.model.ResultadoMedico;
import co.edu.uniquindio.repository.ResultadoMedicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/resultados")
@CrossOrigin(origins = "http://localhost:8080")
public class ResultadoMedicoController {

    private final ResultadoMedicoService resultadoMedicoService;

    public ResultadoMedicoController(ResultadoMedicoService resultadoMedicoService) {
        this.resultadoMedicoService = resultadoMedicoService;
    }

    @GetMapping
    public List<ResultadoMedico> listarResultados() {
        return resultadoMedicoService.listarResultados();
    }

    @GetMapping("/cita/{citaId}")
    public List<ResultadoMedico> listarPorCita(@PathVariable Long citaId) {
        return resultadoMedicoService.listarPorCita(citaId);
    }

    @PostMapping
    public ResultadoMedico guardar(@RequestBody ResultadoMedico resultado) {
        return resultadoMedicoService.guardar(resultado);
    }
}
