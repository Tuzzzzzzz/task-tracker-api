package by.evgen.task_traker_api.controller;

import by.evgen.task_traker_api.dto.ProjectRequest;
import by.evgen.task_traker_api.dto.ProjectResponse;
import by.evgen.task_traker_api.dto.ProjectShortResponse;
import by.evgen.task_traker_api.service.ProjectService;
import by.evgen.task_traker_api.service.security.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(
        path = "/api/projects",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ProjectController {
    private final ProjectService projectService;
    private final JwtTokenService jwtTokenService;

    @GetMapping
    public ResponseEntity<List<ProjectShortResponse>> findAll(
            @CookieValue(name = "accessToken") String token
    ){
        return ResponseEntity.ok(
                projectService.findAllByUserId(jwtTokenService.extractUserId(token))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> findById(
            @PathVariable("id") Long id
    ){
        return ResponseEntity.ok(projectService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProjectShortResponse> create(
            @RequestBody ProjectRequest project,
            @CookieValue(name = "accessToken") String token
    ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(projectService.create(
                        jwtTokenService.extractUserId(token),
                        project)
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectShortResponse> update(
            @PathVariable("id") Long id,
            @RequestBody ProjectRequest project
    ){
        return ResponseEntity.ok(projectService.update(id, project));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        if(!projectService.delete(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.noContent().build();
    }
}

