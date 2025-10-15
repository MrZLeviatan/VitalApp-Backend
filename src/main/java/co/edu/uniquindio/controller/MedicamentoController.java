package co.edu.uniquindio.controller;

import co.edu.uniquindio.model.Medicamento;
import co.edu.uniquindio.repository.MedicamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/medicamentos")
@CrossOrigin(origins = "http://localhost:8080")
public class MedicamentoController {

    private final MedicamentoService medicamentoService;

    public MedicamentoController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    @GetMapping
    public List<Medicamento> listarMedicamentos() {
        return medicamentoService.listarMedicamentos();
    }

    @PostMapping
    public Medicamento guardar(@RequestBody Medicamento medicamento) {
        return medicamentoService.guardar(medicamento);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        medicamentoService.eliminar(id);
    }
}
