package co.edu.uniquindio.mapper.tools;

import co.edu.uniquindio.dto.Telefono.RegistroTelefonoDto;
import co.edu.uniquindio.dto.Telefono.TelefonoDto;
import co.edu.uniquindio.models.tools.Telefono;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TelefonoMapper {

    // Ignorar id y la relación inversa
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "medico", ignore = true)
    Telefono toEntity(RegistroTelefonoDto dto);


    TelefonoDto toDto(Telefono telefono);

}
