package by.evgen.task_traker_api.exception;

public class StageNotFoundException extends RuntimeException {
    public StageNotFoundException(Long stageId) {
        super("Stage with ID " + stageId + " not found");
    }
}
