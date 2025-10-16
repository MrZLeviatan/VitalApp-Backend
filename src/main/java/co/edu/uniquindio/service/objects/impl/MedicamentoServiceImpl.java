package co.edu.uniquindio.service.objects.impl;


import co.edu.uniquindio.dto.medicamento.MedicamentoDto;
import co.edu.uniquindio.dto.medicamento.RegistrarMedicamentoDto;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.mapper.objects.MedicamentoMapper;
import co.edu.uniquindio.models.objects.Medicamento;
import co.edu.uniquindio.repository.objects.MedicamentoRepo;
import co.edu.uniquindio.service.objects.MedicamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicamentoServiceImpl implements MedicamentoService {


    private final MedicamentoMapper medicamentoMapper;
    private final MedicamentoRepo medicamentoRepo;



    @Override
    public void registrarMedicamento(RegistrarMedicamentoDto registrarMedicamentoDto) {

        // Creamos el Medicamento mediante el mapeo
        Medicamento medicamento = medicamentoMapper.toEntity(registrarMedicamentoDto);

        // Guardamos el medicamento en la BD
        medicamentoRepo.save(medicamento);
    }


    @Override
    public MedicamentoDto obtenerMedicamentoDtoId(Long idMedicamento) throws ElementoNoEncontradoException {
        return medicamentoMapper.toDto(obtenerMedicamentoId(idMedicamento));
    }

    @Override
    public Medicamento obtenerMedicamentoId(Long idMedicamento) throws ElementoNoEncontradoException {
        return medicamentoRepo.findById(idMedicamento)
                .orElseThrow(()-> new ElementoNoEncontradoException("Medicamento no registrada en el sistema."));
    }


    @Override
    public List<MedicamentoDto> listarMedicamentos() {
        return medicamentoRepo.findAll()
                .stream()
                .map(medicamentoMapper::toDto)
                .toList();
    }


}
