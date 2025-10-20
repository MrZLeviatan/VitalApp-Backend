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
    
    // Mapeo de nombres y datos adicionales del paciente
    @Mapping(target = "nombrePaciente", source = "paciente.nombre")
    @Mapping(target = "epsPaciente", source = "paciente.eps.nombre")
    @Mapping(target = "telefonoPaciente", expression = "java(obtenerPrimerTelefono(cita.getPaciente()))")
    
    // Mapeo de datos del médico
    @Mapping(target = "nombreMedico", source = "medico.nombre")
    @Mapping(target = "especialidadMedico", source = "medico.especialidad.especialidad")
    
    // Mapeo de datos de agenda
    @Mapping(target = "fecha", source = "agenda.dia")
    @Mapping(target = "horaInicio", source = "agenda.horaInicio")
    @Mapping(target = "horaFin", source = "agenda.horaFin")
    CitaDto toDto(Cita cita);

    /**
     * Método helper para obtener el primer teléfono del paciente
     */
    default String obtenerPrimerTelefono(co.edu.uniquindio.models.users.Paciente paciente) {
        if (paciente != null && paciente.getTelefonos() != null && !paciente.getTelefonos().isEmpty()) {
            return paciente.getTelefonos().get(0).getNumero();
        }
        return null;
    }

}
