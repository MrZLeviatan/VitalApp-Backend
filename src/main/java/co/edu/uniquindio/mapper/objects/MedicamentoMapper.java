package co.edu.uniquindio.mapper.objects;

import co.edu.uniquindio.dto.medicamento.MedicamentoDto;
import co.edu.uniquindio.dto.medicamento.RegistrarMedicamentoDto;
import co.edu.uniquindio.models.objects.Medicamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MedicamentoMapper {


    @Mapping(target = "id", ignore = true)
    Medicamento toEntity(RegistrarMedicamentoDto registrarMedicamentoDto);

    
    MedicamentoDto toDto (Medicamento medicamento);

}
