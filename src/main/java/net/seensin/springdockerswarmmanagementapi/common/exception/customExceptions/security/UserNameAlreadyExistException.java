package net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions.security;

import net.seensin.springdockerswarmmanagementapi.common.exception.ExceptionsTemplate;
import net.seensin.springdockerswarmmanagementapi.common.exception.dto.ErrorMap;
public class UserNameAlreadyExistException extends ExceptionsTemplate {

    public UserNameAlreadyExistException() {
    }

    public UserNameAlreadyExistException(String username) {
        super("user with username : " + username + " already exist ..");
    }

    public UserNameAlreadyExistException(String username, Throwable cause) {
        super("user with username : " + username + " already exist ..");
    }

    @Override
    public int getStatus() {
        return ErrorMap.USER_NAME_ALREADY_EXIST.value();
    }

    @Override
    public ErrorMap getError() {
        return ErrorMap.USER_NAME_ALREADY_EXIST;
    }

}
