package by.evgen.task_traker_api.mapper;

import by.evgen.task_traker_api.database.entity.Stage;
import by.evgen.task_traker_api.dto.StageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = TaskMapper.class
)
public interface StageMapper {
    @Mapping(target = "tasks", source = "tasks")
    StageResponse toResponse(Stage stage);
}
