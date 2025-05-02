package by.evgen.task_traker_api.service;

import by.evgen.task_traker_api.database.entity.Project;
import by.evgen.task_traker_api.database.entity.Stage;
import by.evgen.task_traker_api.database.repository.ProjectRepo;
import by.evgen.task_traker_api.database.repository.StageRepo;
import by.evgen.task_traker_api.dto.*;
import by.evgen.task_traker_api.exception.InvalidOrderNumberException;
import by.evgen.task_traker_api.exception.ProjectNotFoundException;
import by.evgen.task_traker_api.exception.StageNotFoundException;
import by.evgen.task_traker_api.mapper.StageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StageService {
    private final StageRepo stageRepo;
    private final ProjectRepo projectRepo;
    private final StageMapper stageMapper;

    private Long insertOrderNumber(Long projectId, Long orderNumber) {
        Long currentMaxOrderNumber = stageRepo.findMaxOrderNumberByProjectId(projectId).orElse(0L);
        if (orderNumber == null) {
            orderNumber = currentMaxOrderNumber + 1;
        } else if (orderNumber >= 1 && orderNumber <= currentMaxOrderNumber) {
            stageRepo.findByProjectIdAndOrderNumberGreaterThanEqual(projectId, orderNumber)
                    .forEach(stage -> stage.setOrderNumber(stage.getOrderNumber() + 1));
        } else {
            throw new InvalidOrderNumberException(orderNumber);
        }
        return orderNumber;
    }

    @Transactional
    public StageResponse create(Long projectId, StageRequest stageRequest) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));

        Long orderNumber = insertOrderNumber(projectId, stageRequest.orderNumber());

        Stage stage = Stage.builder()
                .name(stageRequest.name())
                .createdAt(Instant.now())
                .orderNumber(orderNumber)
                .project(project)
                .build();

        return stageMapper.toResponse(stageRepo.save(stage));
    }


    @Transactional
    public StageResponse update(Long id, StageRequest stageRequest) {
        Stage stage = stageRepo.findById(id)
                .orElseThrow(() -> new StageNotFoundException(id));

        Long orderNumber = insertOrderNumber(stage.getProject().getId(), stageRequest.orderNumber());

        stage.setName(stageRequest.name());
        stage.setOrderNumber(orderNumber);
        Stage updatedStage = stageRepo.saveAndFlush(stage);
        return stageMapper.toResponse(updatedStage);
    }

    private Long deleteOrderNumber(Long projectId, Long orderNumber) {
        stageRepo.findByProjectIdAndOrderNumberGreaterThanEqual(projectId, orderNumber)
                .forEach(stage -> stage.setOrderNumber(stage.getOrderNumber() - 1));

        return orderNumber;
    }

    @Transactional
    public boolean delete(Long id) {
        Optional<Stage> optionalStage = stageRepo.findById(id);

        return optionalStage.map(stage -> {
                    stageRepo.delete(stage);
                    deleteOrderNumber(stage.getProject().getId(), id);
                    stageRepo.flush();
                    return true;
                })
                .orElse(false);
    }
}
