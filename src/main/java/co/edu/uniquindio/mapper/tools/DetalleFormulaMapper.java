package co.edu.uniquindio.mapper.tools;

import co.edu.uniquindio.dto.detalleFormula.DetalleFormulaDto;
import co.edu.uniquindio.dto.detalleFormula.RegistroDetalleFormulaDto;
import co.edu.uniquindio.models.objects.DetalleFormula;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DetalleFormulaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "formula", ignore = true)
    @Mapping(target = "medicamento", ignore = true)
    DetalleFormula toEntity(RegistroDetalleFormulaDto registroDetalleFormulaDto);

    @Mapping(target = "idFormula", source = "formula.id")
    @Mapping(target = "idMedicamento", source = "medicamento.id")
    DetalleFormulaDto toDto(DetalleFormula detalleFormula);

}
