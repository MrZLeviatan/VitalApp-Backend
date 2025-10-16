package co.edu.uniquindio.service.utils.impl;

import co.edu.uniquindio.dto.eps.EpsDto;
import co.edu.uniquindio.dto.eps.RegistrarEpsDto;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.mapper.tools.EpsMapper;
import co.edu.uniquindio.models.objects.Eps;
import co.edu.uniquindio.models.users.Paciente;
import co.edu.uniquindio.repository.objects.EpsRepo;
import co.edu.uniquindio.service.utils.EpsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EpsServiceImpl implements EpsService {


    private final EpsRepo epsRepo;
    private final EpsMapper epsMapper;


    @Override
    public void registrarEps(RegistrarEpsDto registrarEpsDto) {

        // Se mapea del Dto a la clase Objeto
        Eps eps = epsMapper.toEntity(registrarEpsDto);
        // Se guarda la eps
        epsRepo.save(eps);
    }


    @Override
    public EpsDto obtenerEpsDto(Long idEps)
            throws ElementoNoEncontradoException {
        return epsMapper.toDto(encontrarEps(idEps));
    }


    @Override
    public Eps encontrarEps(Long id) throws ElementoNoEncontradoException {
        return epsRepo.findById(id)
                .orElseThrow(() -> new ElementoNoEncontradoException("Eps no registrada en el sistema"));
    }



    @Override
    public List<EpsDto> listarEpsDto() {
        return epsRepo.findAll()
                .stream()
                .map(epsMapper::toDto)
                .toList();
    }

    @Override
    public void agregarPacienteEps(Eps eps,Paciente paciente) {
        eps.getPacientes().add(paciente);
        epsRepo.save(eps);
    }
}
