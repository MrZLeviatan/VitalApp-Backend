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
public class AlertaService {

    private final AlertaRepository alertaRepository;

    public AlertaService(AlertaRepository alertaRepository) {
        this.alertaRepository = alertaRepository;
    }

    public List<Alerta> listarAlertas() {
        return alertaRepository.findAll();
    }

    public List<Alerta> listarPorPaciente(Long pacienteId) {
        return alertaRepository.findByPacienteId(pacienteId);
    }

    public List<Alerta> listarNoLeidas() {
        return alertaRepository.findByIsLeidaFalse();
    }

    public Alerta guardar(Alerta alerta) {
        return alertaRepository.save(alerta);
    }

    public void marcarComoLeida(Long id) {
        alertaRepository.findById(id).ifPresent(alerta -> {
            alerta.setLeida(true);
            alertaRepository.save(alerta);
        });
    }
}

