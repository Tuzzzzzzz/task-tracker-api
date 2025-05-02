package by.evgen.task_traker_api.exception;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(Long projectId) {
        super("Project with ID " + projectId + " not found");
    }
}
