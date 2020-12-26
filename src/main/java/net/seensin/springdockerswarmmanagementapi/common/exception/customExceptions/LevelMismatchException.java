package net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions;

public class LevelMismatchException extends  Exception {

    public LevelMismatchException() {
        super("Input Level Doesn't match with oid extension level");

    }

    public LevelMismatchException(String message) {
        super(message);
    }

    public LevelMismatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
