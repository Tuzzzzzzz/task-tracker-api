package by.evgen.task_traker_api.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long taskId) {
      super("Task with ID " + taskId + " not found");
    }
}
