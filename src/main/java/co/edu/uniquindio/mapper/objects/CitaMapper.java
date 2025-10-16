package co.edu.uniquindio.mapper.objects;


import co.edu.uniquindio.dto.cita.CitaDto;
import co.edu.uniquindio.dto.cita.RegistrarCitaDto;
import co.edu.uniquindio.models.objects.Cita;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CitaMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estadoCita", constant = "AGENDADA")
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "agenda", ignore = true)
    Cita toEntity(RegistrarCitaDto registrarCitaDto);


    @Mapping(target = "idPaciente", source = "paciente.id")
    @Mapping(target = "idMedico", source = "medico.id")
    @Mapping(target = "idFormula", source = "formula.id")
    @Mapping(target = "idAgenda", source = "agenda.id")
    CitaDto toDto(Cita cita);

}
