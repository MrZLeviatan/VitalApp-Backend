package co.edu.uniquindio.mapper.objects;

import co.edu.uniquindio.dto.formula.FormulaDto;
import co.edu.uniquindio.dto.formula.RegistroFormulaDto;
import co.edu.uniquindio.mapper.tools.DetalleFormulaMapper;
import co.edu.uniquindio.models.objects.Formula;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
    uses = {DetalleFormulaMapper.class})
public interface FormulaMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "cita", ignore = true)
    @Mapping(target = "fechaRegistro", expression = "java(java.time.LocalDate.now())")
    Formula toEntity(RegistroFormulaDto registroFormulaDto);

    @Mapping(target = "idPaciente", source = "paciente.id")
    @Mapping(target = "idCita", source = "cita.id")
    FormulaDto toDto(Formula formula);
}
