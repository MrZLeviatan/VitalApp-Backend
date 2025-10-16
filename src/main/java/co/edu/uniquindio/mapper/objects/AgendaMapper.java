package co.edu.uniquindio.mapper.objects;

import co.edu.uniquindio.dto.agenda.AgendaDto;
import co.edu.uniquindio.models.objects.Agenda;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AgendaMapper {

    @Mapping(target = "idCita", source = "cita.id")
    @Mapping(target = "isActivo", source = "activo")
    AgendaDto toDto(Agenda agenda);
}
