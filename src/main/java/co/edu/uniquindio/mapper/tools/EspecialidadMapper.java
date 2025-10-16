package co.edu.uniquindio.mapper.tools;

import co.edu.uniquindio.dto.especialidad.AgregarEspecialidadDto;
import co.edu.uniquindio.dto.especialidad.EspecialidadDto;
import co.edu.uniquindio.models.tools.Especialidad;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EspecialidadMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "medicos", expression = "java(new java.util.ArrayList<>())")
    Especialidad toEntity(AgregarEspecialidadDto agregarEspecialidadDto);


    EspecialidadDto toDto(Especialidad especialidad);

}
