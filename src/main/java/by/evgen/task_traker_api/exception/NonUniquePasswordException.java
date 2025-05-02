package by.evgen.task_traker_api.exception;

public class NonUniquePasswordException extends RuntimeException {
    public NonUniquePasswordException(String password) {
        super("""
                New password must differ from current.
                Password " + password + " is non-unique
                """);
    }
}
