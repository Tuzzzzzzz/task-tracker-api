package by.evgen.task_traker_api.mapper;

import by.evgen.task_traker_api.database.entity.Project;
import by.evgen.task_traker_api.dto.ProjectResponse;
import by.evgen.task_traker_api.dto.ProjectShortResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = StageMapper.class
)
public interface ProjectMapper {
    @Mapping(target = "stages", source = "stages")
    ProjectResponse toResponse(Project project);

    ProjectShortResponse toShortResponse(Project project);
}
