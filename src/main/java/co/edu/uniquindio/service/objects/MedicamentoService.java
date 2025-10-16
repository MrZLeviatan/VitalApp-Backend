package co.edu.uniquindio.service.objects;

import co.edu.uniquindio.dto.medicamento.MedicamentoDto;
import co.edu.uniquindio.dto.medicamento.RegistrarMedicamentoDto;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.models.objects.Medicamento;

import java.util.List;

public interface MedicamentoService {

    // (ADMIN)
    void registrarMedicamento(RegistrarMedicamentoDto registrarMedicamentoDto);

    // (ADMIN, MEDICO A LA HORA DE REGISTRAR FORMULA)
    MedicamentoDto obtenerMedicamentoDtoId(Long idMedicamento) throws ElementoNoEncontradoException;

    // SISTEMA
    Medicamento obtenerMedicamentoId(Long idMedicamento) throws ElementoNoEncontradoException;

    // (ADMIN, MEDICO A LA HORA DE REGISTRAR FORMULA)
    List<MedicamentoDto> listarMedicamentos();



}
