package by.evgen.task_traker_api.exception;

public class InvalidOrderNumberException extends RuntimeException {
    public InvalidOrderNumberException(Long orderNumber) {
        super("Stage with order number " + orderNumber + " detached from list of stages" );
    }
}
