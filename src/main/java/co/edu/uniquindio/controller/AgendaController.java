package co.edu.uniquindio.controller;

import co.edu.uniquindio.model.Agenda;
import co.edu.uniquindio.repository.AgendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/agendas")
@CrossOrigin(origins = "http://localhost:8080")
public class AgendaController {

    private final AgendaService agendaService;

    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @GetMapping
    public List<Agenda> listarAgendas() {
        return agendaService.listarAgendas();
    }

    @PostMapping
    public Agenda guardar(@RequestBody Agenda agenda) {
        return agendaService.guardar(agenda);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        agendaService.eliminar(id);
    }
}
