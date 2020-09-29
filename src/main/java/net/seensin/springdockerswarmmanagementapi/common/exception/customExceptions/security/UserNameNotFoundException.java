package net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions.security;

import net.seensin.springdockerswarmmanagementapi.common.exception.ExceptionsTemplate;
import net.seensin.springdockerswarmmanagementapi.common.exception.dto.ErrorMap;
public class UserNameNotFoundException extends ExceptionsTemplate {

    public UserNameNotFoundException() {
    }

    public UserNameNotFoundException(String username) {
        super("Could not find User with username : " + username);
    }

    public UserNameNotFoundException(String username, Throwable cause) {
        super("Could not find User with username : " + username);
    }

    @Override
    public int getStatus() {
        return ErrorMap.USER_NAME_NOT_FOUND.value();
    }

    @Override
    public ErrorMap getError() {
        return ErrorMap.USER_NAME_NOT_FOUND;
    }
}
