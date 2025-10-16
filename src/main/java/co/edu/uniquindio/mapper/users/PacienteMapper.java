package co.edu.uniquindio.mapper.users;

import co.edu.uniquindio.dto.paciente.EditarPacienteDto;
import co.edu.uniquindio.dto.paciente.PacienteDto;
import co.edu.uniquindio.dto.paciente.RegistrarPacienteDto;
import co.edu.uniquindio.mapper.tools.TelefonoMapper;
import co.edu.uniquindio.models.users.Paciente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",
        uses = {TelefonoMapper.class})
public interface PacienteMapper {

    /**
     * Método para transformar un RegistroPaciente en un Paciente
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "crearUserDto")
    @Mapping(target = "user.estadoUser", constant = "ACTIVO")
    @Mapping(target = "eps", ignore = true)
    @Mapping(target = "ciudad", ignore = true)
    @Mapping(target = "citas", expression = "java(new java.util.ArrayList<>())")
    @Mapping(target = "formulasMedicas", expression = "java(new java.util.ArrayList<>())")
    Paciente toEntity(RegistrarPacienteDto registrarPacienteDto);


    // Método para transformar un Paciente a una clase Dto
    @Mapping(target = "idEps", source = "eps.id")
    PacienteDto toDto(Paciente paciente);

    /**
     * Método para actualizar un Paciente mediante el Mapeo con el Dto
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "eps", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "citas", ignore = true)
    @Mapping(target = "formulasMedicas", ignore = true)
    @Mapping(target = "ciudad", ignore = true)
    @Mapping(target = "telefonos", ignore = true)
    void updateEntity (EditarPacienteDto editarPacienteDto, @MappingTarget Paciente paciente);

}
