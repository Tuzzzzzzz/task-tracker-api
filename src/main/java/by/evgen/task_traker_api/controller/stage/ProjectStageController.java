package by.evgen.task_traker_api.controller.stage;

import by.evgen.task_traker_api.dto.StageRequest;
import by.evgen.task_traker_api.dto.StageResponse;
import by.evgen.task_traker_api.service.StageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        path = "/api/projects/{projectId}/stages",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ProjectStageController {
    private final StageService stageService;

    @PostMapping
    public ResponseEntity<StageResponse> createStage(
            @PathVariable("projectId") Long projectId,
            @RequestBody StageRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(stageService.create(projectId, request));
    }
}
