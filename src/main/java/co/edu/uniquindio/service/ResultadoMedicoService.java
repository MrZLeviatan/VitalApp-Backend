
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

public class ResultadoMedicoService {

    private final ResultadoMedicoRepository resultadoMedicoRepository;

    public ResultadoMedicoService(ResultadoMedicoRepository resultadoMedicoRepository) {
        this.resultadoMedicoRepository = resultadoMedicoRepository;
    }

    public List<ResultadoMedico> listarResultados() {
        return resultadoMedicoRepository.findAll();
    }

    public List<ResultadoMedico> listarPorCita(Long citaId) {
        return resultadoMedicoRepository.findByCitaId(citaId);
    }

    public ResultadoMedico guardar(ResultadoMedico resultado) {
        return resultadoMedicoRepository.save(resultado);
    }
}
