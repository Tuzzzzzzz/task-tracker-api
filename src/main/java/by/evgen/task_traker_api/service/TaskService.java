package by.evgen.task_traker_api.service;

import by.evgen.task_traker_api.database.entity.Stage;
import by.evgen.task_traker_api.database.entity.Task;
import by.evgen.task_traker_api.database.repository.StageRepo;
import by.evgen.task_traker_api.database.repository.TaskRepo;
import by.evgen.task_traker_api.dto.TaskRequest;
import by.evgen.task_traker_api.dto.TaskResponse;
import by.evgen.task_traker_api.exception.StageNotFoundException;
import by.evgen.task_traker_api.exception.TaskNotFoundException;
import by.evgen.task_traker_api.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {
    private final StageRepo stageRepo;
    private final TaskRepo taskRepo;
    private final TaskMapper taskMapper;

    public TaskResponse create(Long stageId, TaskRequest taskRequest){
        Stage stage = stageRepo.findById(stageId)
                .orElseThrow(() -> new StageNotFoundException(stageId));

        Task task = Task.builder()
                .name(taskRequest.name())
                .description(taskRequest.description())
                .createdAt(Instant.now())
                .stage(stage)
                .build();

        return taskMapper.toResponse(taskRepo.save(task));
    }

    @Transactional
    public TaskResponse update(Long id, TaskRequest taskRequest) {
        return taskRepo.findById(id)
                .map(task -> {
                    task.setName(taskRequest.name());
                    task.setDescription(taskRequest.description());
                    Task updatedTask = taskRepo.saveAndFlush(task);
                    return taskMapper.toResponse(updatedTask);
                })
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Transactional
    public boolean delete(Long id) {
        return taskRepo.findById(id)
                .map(task -> {
                    taskRepo.delete(task);
                    taskRepo.flush();
                    return true;
                })
                .orElse(false);
    }
}
