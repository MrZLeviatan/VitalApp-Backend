
package co.edu.uniquindio.service;

import co.edu.uniquindio.model.*;
import co.edu.uniquindio.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class ExamenService {

    private final ExamenRepository examenRepository;

    public ExamenService(ExamenRepository examenRepository) {
        this.examenRepository = examenRepository;
    }

    public List<Examen> listarExamenes() {
        return examenRepository.findAll();
    }

    public Examen guardar(Examen examen) {
        return examenRepository.save(examen);
    }
}
