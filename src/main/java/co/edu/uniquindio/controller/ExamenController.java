package co.edu.uniquindio.controller;

import co.edu.uniquindio.model.Examen;
import co.edu.uniquindio.repository.ExamenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/examenes")
@CrossOrigin(origins = "http://localhost:8080")
public class ExamenController {

    private final ExamenService examenService;

    public ExamenController(ExamenService examenService) {
        this.examenService = examenService;
    }

    @GetMapping
    public List<Examen> listarExamenes() {
        return examenService.listarExamenes();
    }

    @PostMapping
    public Examen guardar(@RequestBody Examen examen) {
        return examenService.guardar(examen);
    }
}
