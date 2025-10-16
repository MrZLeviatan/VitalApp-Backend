package co.edu.uniquindio.mapper.objects;

import co.edu.uniquindio.dto.alerta.AlertaDto;
import co.edu.uniquindio.models.objects.Alerta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AlertaMapper {


    @Mapping(target = "idPaciente", source = "paciente.id")
    AlertaDto toDto(Alerta alerta);

}
