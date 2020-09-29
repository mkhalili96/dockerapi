package net.seensin.springdockerswarmmanagementapi.common.exception;


import net.seensin.springdockerswarmmanagementapi.common.exception.dto.ErrorMap;

public abstract class ExceptionsTemplate extends Exception {

    public ExceptionsTemplate() {
    }

    public ExceptionsTemplate(String message) {
        super(message);
    }

    public ExceptionsTemplate(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatus();

    public abstract ErrorMap getError();
}
