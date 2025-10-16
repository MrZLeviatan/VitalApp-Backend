package co.edu.uniquindio.service.objects;

import co.edu.uniquindio.dto.alerta.AlertaDto;
import co.edu.uniquindio.dto.alerta.MarcarLeidaAlertaDto;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.models.enums.TipoAlerta;
import co.edu.uniquindio.models.users.Paciente;

import java.util.List;

public interface AlertaService {


    void crearAlerta(Paciente paciente, TipoAlerta tipoAlerta, String mensaje);

    List<AlertaDto> listarAlertasPaciente(Long idPaciente);


    AlertaDto obtenerAlerta(Long idAlerta) throws ElementoNoEncontradoException;

    void marcarAlertaLeida(MarcarLeidaAlertaDto marcarLeidaAlertaDto)
            throws ElementoNoEncontradoException;


}
