package co.edu.uniquindio.controller;

import co.edu.uniquindio.model.Alerta;
import co.edu.uniquindio.repository.AlertaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/alertas")
@CrossOrigin(origins = "http://localhost:8080")
public class AlertaController {

    private final AlertaService alertaService;

    public AlertaController(AlertaService alertaService) {
        this.alertaService = alertaService;
    }

    @GetMapping
    public List<Alerta> listarAlertas() {
        return alertaService.listarAlertas();
    }

    @GetMapping("/paciente/{pacienteId}")
    public List<Alerta> listarPorPaciente(@PathVariable Long pacienteId) {
        return alertaService.listarPorPaciente(pacienteId);
    }

    @GetMapping("/noleidas")
    public List<Alerta> listarNoLeidas() {
        return alertaService.listarNoLeidas();
    }

    @PostMapping
    public Alerta guardar(@RequestBody Alerta alerta) {
        return alertaService.guardar(alerta);
    }

    @PutMapping("/{id}/leida")
    public void marcarComoLeida(@PathVariable Long id) {
        alertaService.marcarComoLeida(id);
    }
}
