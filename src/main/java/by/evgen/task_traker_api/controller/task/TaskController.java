package by.evgen.task_traker_api.controller.task;

import by.evgen.task_traker_api.dto.TaskRequest;
import by.evgen.task_traker_api.dto.TaskResponse;
import by.evgen.task_traker_api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        path = "/api/tasks",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class TaskController {
    private final TaskService taskService;

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> update(
            @PathVariable("id") Long id,
            @RequestBody TaskRequest request
    ){
        return ResponseEntity.ok(taskService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        if(!taskService.delete(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.noContent().build();
    }
}
