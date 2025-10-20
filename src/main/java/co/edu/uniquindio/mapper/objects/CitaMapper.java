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


    // Mapeo de IDs
    @Mapping(target = "idPaciente", source = "paciente.id")
    @Mapping(target = "idMedico", source = "medico.id")
    @Mapping(target = "idFormula", source = "formula.id")
    @Mapping(target = "idAgenda", source = "agenda.id")
    
    // Mapeo de nombres y datos adicionales
    @Mapping(target = "nombrePaciente", source = "paciente.nombre")
    @Mapping(target = "nombreMedico", source = "medico.nombre")
    @Mapping(target = "especialidadMedico", source = "medico.especialidad.especialidad")
    
    // Mapeo de datos de agenda
    @Mapping(target = "fecha", source = "agenda.dia")
    @Mapping(target = "horaInicio", source = "agenda.horaInicio")
    @Mapping(target = "horaFin", source = "agenda.horaFin")
    CitaDto toDto(Cita cita);

}
