package co.edu.uniquindio.service;

import co.edu.uniquindio.model.Agenda;
import co.edu.uniquindio.repository.PacienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class AgendaService {

    private final AgendaRepository agendaRepository;

    public AgendaService(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public List<Agenda> listarAgendas() {
        return agendaRepository.findAll();
    }

    public List<Agenda> buscarPorDia(DayOfWeek dia) {
        return agendaRepository.findByDia(dia);
    }

    public Agenda guardar(Agenda agenda) {
        return agendaRepository.save(agenda);
    }

    public void eliminar(Long id) {
        agendaRepository.deleteById(id);
    }
}
