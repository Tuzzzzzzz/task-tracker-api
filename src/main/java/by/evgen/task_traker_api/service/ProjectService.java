package by.evgen.task_traker_api.service;

import by.evgen.task_traker_api.database.entity.Project;
import by.evgen.task_traker_api.database.entity.User;
import by.evgen.task_traker_api.database.repository.ProjectRepo;
import by.evgen.task_traker_api.database.repository.UserRepo;
import by.evgen.task_traker_api.dto.ProjectRequest;
import by.evgen.task_traker_api.dto.ProjectResponse;
import by.evgen.task_traker_api.dto.ProjectShortResponse;
import by.evgen.task_traker_api.exception.ProjectNotFoundException;
import by.evgen.task_traker_api.exception.UserNotFoundException;
import by.evgen.task_traker_api.mapper.ProjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {
    private final ProjectRepo projectRepo;
    private final UserRepo userRepo;
    private final ProjectMapper projectMapper;

    @Transactional
    public ProjectShortResponse create(Long userId, ProjectRequest projectRequest){
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Project savedProject = projectRepo.save(
                Project.builder()
                        .name(projectRequest.name())
                        .description(projectRequest.description())
                        .createdAt(Instant.now())
                        .user(user)
                        .build()
        );

        return projectMapper.toShortResponse(savedProject);
    }

    public List<ProjectShortResponse> findAllByUserId(Long id) {
        List<Project> projects = projectRepo.findAllByUserId(id);
        if (projects.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        return projects.stream()
                .map(projectMapper::toShortResponse)
                .toList();
    }

    public ProjectResponse findById(Long id){
        return projectRepo.findById(id)
                .map(projectMapper::toResponse)
                .orElseThrow(() -> new ProjectNotFoundException(id));
    }

    @Transactional
    public ProjectShortResponse update(Long id, ProjectRequest projectRequest) {
        return projectRepo.findById(id)
                .map(project -> {
                    project.setName(projectRequest.name());
                    project.setDescription(projectRequest.description());
                    Project updatedProject = projectRepo.saveAndFlush(project);
                    return projectMapper.toShortResponse(updatedProject);
                })
                .orElseThrow(() -> new ProjectNotFoundException(id));
    }

    @Transactional
    public boolean delete(Long id) {
        return projectRepo.findById(id)
                .map(project -> {
                    projectRepo.delete(project);
                    projectRepo.flush();
                    return true;
                })
                .orElse(false);
    }
}
