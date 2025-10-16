package co.edu.uniquindio.service.utils.impl;

import co.edu.uniquindio.dto.especialidad.AgregarEspecialidadDto;
import co.edu.uniquindio.dto.especialidad.EspecialidadDto;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.mapper.tools.EspecialidadMapper;
import co.edu.uniquindio.models.tools.Especialidad;
import co.edu.uniquindio.models.users.Medico;
import co.edu.uniquindio.repository.objects.EspecialidadRepo;
import co.edu.uniquindio.service.utils.EspecialidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EspecialidadServiceImpl implements EspecialidadService {


    private final EspecialidadRepo especialidadRepo;
    private final EspecialidadMapper especialidadMapper;



    @Override
    public void registrarEspecialidad(AgregarEspecialidadDto agregarEspecialidadDto) {

        // 1. Se mapea la especialidad
        Especialidad especialidad = especialidadMapper.toEntity(agregarEspecialidadDto);
        // Se guarda en la BD
        especialidadRepo.save(especialidad);
    }


    @Override
    public Especialidad obtenerEspecialidad(Long idEspecialidad) throws ElementoNoEncontradoException {
        return especialidadRepo.findById(idEspecialidad)
                .orElseThrow(()-> new ElementoNoEncontradoException("Especialidad no registrado en el sistema."));
    }


    @Override
    public EspecialidadDto obtenerEspecialidadDto(Long idEspecialidad) throws ElementoNoEncontradoException {
        return especialidadMapper.toDto(obtenerEspecialidad(idEspecialidad));
    }


    @Override
    public List<EspecialidadDto> obtenerEspecialidades() {
        return especialidadRepo.findAll()
                .stream()
                .map(especialidadMapper::toDto)
                .toList();
    }


    @Override
    public void agregarMedicoEspecialidad(Especialidad especialidad, Medico medico) {
        especialidad.getMedicos().add(medico);
        especialidadRepo.save(especialidad);
    }

}
