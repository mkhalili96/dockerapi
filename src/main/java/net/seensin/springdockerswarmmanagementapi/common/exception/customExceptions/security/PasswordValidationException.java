package net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions.security;

import net.seensin.springdockerswarmmanagementapi.common.exception.ExceptionsTemplate;
import net.seensin.springdockerswarmmanagementapi.common.exception.dto.ErrorMap;

import java.util.List;

public class PasswordValidationException extends ExceptionsTemplate {


    public PasswordValidationException() {
    }

    public PasswordValidationException(List<String> error) {
        super("password is not valid : " + error);
    }

    public PasswordValidationException(List<String> error, Throwable cause) {
        super("password is not valid : " + error, cause);
    }

    @Override
    public int getStatus() {
        return ErrorMap.PASSWORD_NOT_VALID.value();
    }

    @Override
    public ErrorMap getError() {
        return ErrorMap.PASSWORD_NOT_VALID;
    }
}