package co.edu.uniquindio.service.objects.impl;

import co.edu.uniquindio.dto.alerta.AlertaDto;
import co.edu.uniquindio.dto.alerta.MarcarLeidaAlertaDto;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.mapper.objects.AlertaMapper;
import co.edu.uniquindio.models.enums.TipoAlerta;
import co.edu.uniquindio.models.objects.Alerta;
import co.edu.uniquindio.models.users.Paciente;
import co.edu.uniquindio.repository.objects.AlertaRepo;
import co.edu.uniquindio.service.objects.AlertaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertaServiceImpl implements AlertaService {

    private final AlertaRepo alertaRepo;
    private final AlertaMapper alertaMapper;


    @Override
    @Transactional
    public void crearAlerta(Paciente paciente, TipoAlerta tipoAlerta, String mensaje) {

        Alerta alerta = new Alerta();
        alerta.setPaciente(paciente);
        alerta.setTipoAlerta(tipoAlerta);
        alerta.setMensaje(mensaje);
        alerta.setFechaRegistro(LocalDateTime.now());
        alerta.setLeida(false);
        alertaRepo.save(alerta);
    }

    @Override
    public List<AlertaDto> listarAlertasPaciente(Long idPaciente) {
        return alertaRepo.findByPacienteId(idPaciente)
                .stream()
                .map(alertaMapper::toDto)
                .toList();
    }

    @Override
    public AlertaDto obtenerAlerta(Long idAlerta) throws ElementoNoEncontradoException {
        return alertaRepo.findById(idAlerta)
                .map(alertaMapper::toDto)
                .orElseThrow(()-> new ElementoNoEncontradoException("Alerta no registrada en el sistema"));
    }


    @Override
    public void marcarAlertaLeida(MarcarLeidaAlertaDto marcarLeidaAlertaDto) throws ElementoNoEncontradoException {
        Alerta alerta = alertaRepo.findById(marcarLeidaAlertaDto.idAlerta())
                .orElseThrow(() -> new ElementoNoEncontradoException("Alerta no encontrada"));
        alerta.setLeida(true);
    }


}
