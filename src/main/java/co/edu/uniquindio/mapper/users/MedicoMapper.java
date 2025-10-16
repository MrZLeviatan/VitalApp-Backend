package co.edu.uniquindio.mapper.users;

import co.edu.uniquindio.dto.medico.EditarMedicoDto;
import co.edu.uniquindio.dto.medico.MedicoDto;
import co.edu.uniquindio.dto.medico.RegistrarMedicoDto;
import co.edu.uniquindio.mapper.tools.TelefonoMapper;
import co.edu.uniquindio.models.users.Medico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",
        uses = {TelefonoMapper.class})
public interface MedicoMapper {


    /**
     * Método para transformar un RegistroMedico en un Médico
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "especialidad", ignore = true)
    @Mapping(target = "citas", expression = "java(new java.util.ArrayList<>())")
    @Mapping(target = "agenda", expression = "java(new java.util.ArrayList<>())")
    @Mapping(target = "user.estadoUser", constant = "ACTIVO")
    Medico toDto (RegistrarMedicoDto registrarMedicoDto);

    // Método que transforma una clase Médico a un Dto
    @Mapping(target = "idEspecialidad", source = "especialidad.id")
    MedicoDto toDto (Medico medico);


    /**
     * Método que actualiza los datos del Médico mediante el Mapeo con el Dto
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "agenda", ignore = true)
    @Mapping(target = "citas", ignore = true)
    @Mapping(target = "telefonos", ignore = true)
    void updateEntity(EditarMedicoDto editarMedicoDto, @MappingTarget Medico medico);

}
