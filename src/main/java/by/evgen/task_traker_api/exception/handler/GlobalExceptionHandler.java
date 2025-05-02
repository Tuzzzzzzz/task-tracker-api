package by.evgen.task_traker_api.exception.handler;

import by.evgen.task_traker_api.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice(basePackages = "by.evgen.task_tracker_api.controller")
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({UsernameNotFoundException.class, UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFound(RuntimeException ex) {
        return new ErrorResponse(
                "user.not_found",
                ex.getMessage()
        );
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUsernameAlreadyExists(UsernameAlreadyExistsException ex) {
        return new ErrorResponse(
                "user.already_exists",
                ex.getMessage()
        );
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleProjectNotFound(ProjectNotFoundException ex) {
        return new ErrorResponse(
                "project.not_found",
                ex.getMessage()
        );
    }

    @ExceptionHandler(StageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleStageNotFound(StageNotFoundException ex) {
        return new ErrorResponse(
                "stage.not_found",
                ex.getMessage()
        );
    }

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleTaskNotFound(TaskNotFoundException ex) {
        return new ErrorResponse(
                "task.not_found",
                ex.getMessage()
        );
    }

    @ExceptionHandler(InvalidOrderNumberException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidOrderNumber(InvalidOrderNumberException ex) {
        return new ErrorResponse(
                "stage.invalid_order",
                ex.getMessage()
        );
    }
}