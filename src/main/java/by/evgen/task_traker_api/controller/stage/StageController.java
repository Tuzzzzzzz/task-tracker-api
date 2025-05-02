package by.evgen.task_traker_api.controller.stage;

import by.evgen.task_traker_api.dto.StageRequest;
import by.evgen.task_traker_api.dto.StageResponse;
import by.evgen.task_traker_api.service.StageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        path = "/api/stages",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class StageController {
    private final StageService stageService;

    @PutMapping("/{id}")
    public ResponseEntity<StageResponse> update(
            @PathVariable("id") Long id,
            @RequestBody StageRequest request
    ){
        return ResponseEntity.ok(stageService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        if(!stageService.delete(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.noContent().build();
    }

}
