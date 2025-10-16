package co.edu.uniquindio.service.objects;

import co.edu.uniquindio.dto.detalleFormula.DetalleFormulaDto;
import co.edu.uniquindio.dto.formula.FormulaDto;
import co.edu.uniquindio.dto.formula.RegistroFormulaDto;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.models.objects.Formula;

import java.util.List;

public interface FormulaService {

    // (MEDICO)
    void registrarFormula(RegistroFormulaDto registroFormulaDto)
            throws ElementoNoEncontradoException;

    // SISTEMA
    Formula obtenerFormula(Long idFormula)
            throws ElementoNoEncontradoException;

    // (PACIENTE)
    FormulaDto obtenerFormulaDto(Long idFormula)
            throws ElementoNoEncontradoException;

    // PACIENTE
    List<FormulaDto> obtenerFormulasPaciente(Long idPaciente)
            throws ElementoNoEncontradoException;


    List<DetalleFormulaDto> obtenerDetalleFormula(Long idFormula)
            throws ElementoNoEncontradoException;

}

