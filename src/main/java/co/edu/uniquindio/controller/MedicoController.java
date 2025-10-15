package co.edu.uniquindio.controller;

import co.edu.uniquindio.model.Medico;
import co.edu.uniquindio.repository.MedicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/medicos")
@CrossOrigin(origins = "http://localhost:8080")
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @GetMapping
    public List<Medico> listarMedicos() {
        return medicoService.listarMedicos();
    }

    @GetMapping("/{id}")
    public Medico obtenerPorId(@PathVariable Long id) {
        return medicoService.buscarPorId(id).orElse(null);
    }

    @PostMapping
    public Medico guardar(@RequestBody Medico medico) {
        return medicoService.guardar(medico);
    }

    @PutMapping("/{id}")
    public Medico actualizar(@PathVariable Long id, @RequestBody Medico medico) {
        medico.setId(id);
        return medicoService.guardar(medico);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        medicoService.eliminar(id);
    }
}
