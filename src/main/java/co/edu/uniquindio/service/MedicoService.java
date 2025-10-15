package co.edu.uniquindio.service;

import co.edu.uniquindio.model.Medico;
import co.edu.uniquindio.repository.PacienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;

    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public List<Medico> listarMedicos() {
        return medicoRepository.findAll();
    }

    public Medico guardar(Medico medico) {
        return medicoRepository.save(medico);
    }

    public Optional<Medico> buscarPorId(Long id) {
        return medicoRepository.findById(id);
    }

    public void eliminar(Long id) {
        medicoRepository.deleteById(id);
    }
}
