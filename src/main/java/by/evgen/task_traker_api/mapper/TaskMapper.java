package by.evgen.task_traker_api.mapper;

import by.evgen.task_traker_api.database.entity.Task;
import by.evgen.task_traker_api.dto.TaskRequest;
import by.evgen.task_traker_api.dto.TaskResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {
    TaskResponse toResponse(Task task);
}
