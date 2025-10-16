package co.edu.uniquindio.service.objects.impl;


import co.edu.uniquindio.dto.detalleFormula.DetalleFormulaDto;
import co.edu.uniquindio.dto.detalleFormula.RegistroDetalleFormulaDto;
import co.edu.uniquindio.dto.formula.FormulaDto;
import co.edu.uniquindio.dto.formula.RegistroFormulaDto;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.mapper.objects.FormulaMapper;
import co.edu.uniquindio.mapper.tools.DetalleFormulaMapper;
import co.edu.uniquindio.models.objects.Cita;
import co.edu.uniquindio.models.objects.DetalleFormula;
import co.edu.uniquindio.models.objects.Formula;
import co.edu.uniquindio.models.users.Paciente;
import co.edu.uniquindio.repository.objects.FormulaRepo;
import co.edu.uniquindio.service.objects.CitasService;
import co.edu.uniquindio.service.objects.FormulaService;
import co.edu.uniquindio.service.objects.MedicamentoService;
import co.edu.uniquindio.service.users.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FormulaServiceImpl implements FormulaService {


    private final FormulaRepo formulaRepo;
    private final FormulaMapper formulaMapper;
    private final PacienteService pacienteService;
    private final CitasService citasService;
    private final MedicamentoService medicamentoService;
    private final DetalleFormulaMapper detalleFormulaMapper;


    @Override
    @Transactional
    public void registrarFormula(RegistroFormulaDto registroFormulaDto) throws ElementoNoEncontradoException {

        // Mapeamos la fórmula desde el DTO
        Formula formula = formulaMapper.toEntity(registroFormulaDto);

        // Asignamos paciente y cita
        Paciente paciente = pacienteService.buscarPacienteId(registroFormulaDto.idPaciente());
        formula.setPaciente(paciente);

        Cita cita = citasService.obtenerCita(registroFormulaDto.idCita());
        formula.setCita(cita);

        // Creamos e inicializamos la lista de detalles
        List<DetalleFormula> detalles = new ArrayList<>();

        // Asociamos los detalles si existen
        if (registroFormulaDto.detallesFormula() != null) {
            for (RegistroDetalleFormulaDto registroDetalleFormulaDto : registroFormulaDto.detallesFormula()) {
                // Convertimos el DTO a entidad usando MapStruct
                DetalleFormula detalle = detalleFormulaMapper.toEntity(registroDetalleFormulaDto);

                // Asignamos el medicamento buscándolo por ID
                detalle.setMedicamento(medicamentoService.obtenerMedicamentoId(registroDetalleFormulaDto.idMedicamento()));

                // Asociamos el detalle a la fórmula para cascada
                detalle.setFormula(formula);

                // Agregamos el detalle a la lista
                detalles.add(detalle);
            }
        }

        // Asignamos la lista completa de detalles a la fórmula
        formula.setDetallesFormula(detalles);

        // Guardamos la fórmula junto con los detalles en cascada
        formulaRepo.save(formula);

        // Actualizamos las relaciones de paciente y cita
        pacienteService.agregarFormulaPaciente(paciente, formula);
        citasService.agregarFormulaCita(cita, formula);
    }




    @Override
    public Formula obtenerFormula(Long idFormula) throws ElementoNoEncontradoException {
        return formulaRepo.findById(idFormula)
                .orElseThrow(()-> new ElementoNoEncontradoException("Formula no registrada en el sistema."));
    }

    @Override
    public FormulaDto obtenerFormulaDto(Long idFormula) throws ElementoNoEncontradoException {
        return formulaMapper.toDto(obtenerFormula(idFormula));
    }


    @Override
    public List<FormulaDto> obtenerFormulasPaciente(Long idPaciente) throws ElementoNoEncontradoException {
            Paciente paciente = pacienteService.buscarPacienteId(idPaciente);
        return paciente.getFormulasMedicas().stream()
                .map(formulaMapper::toDto)
                .toList();
    }


    @Override
    public List<DetalleFormulaDto> obtenerDetalleFormula(Long idFormula) throws ElementoNoEncontradoException {
        return obtenerFormula(idFormula).getDetallesFormula().stream()
                .map(detalleFormulaMapper::toDto)
                .toList();
    }
}
