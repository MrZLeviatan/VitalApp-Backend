package co.edu.uniquindio.mapper.tools;

import co.edu.uniquindio.dto.eps.EpsDto;
import co.edu.uniquindio.dto.eps.RegistrarEpsDto;
import co.edu.uniquindio.models.objects.Eps;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EpsMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pacientes", expression = "java(new java.util.ArrayList<>())")
    Eps toEntity(RegistrarEpsDto registrarEpsDto);


    EpsDto toDto(Eps eps);
}
