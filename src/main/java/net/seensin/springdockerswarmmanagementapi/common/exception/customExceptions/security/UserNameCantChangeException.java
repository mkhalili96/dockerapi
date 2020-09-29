package net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions.security;

import net.seensin.springdockerswarmmanagementapi.common.exception.ExceptionsTemplate;
import net.seensin.springdockerswarmmanagementapi.common.exception.dto.ErrorMap;
public class UserNameCantChangeException extends ExceptionsTemplate {

    public UserNameCantChangeException() {
        super("username cant change");
    }

    @Override
    public int getStatus() {
        return ErrorMap.CANT_CHANGE_USERNAME.value();
    }

    @Override
    public ErrorMap getError() {
        return ErrorMap.CANT_CHANGE_USERNAME;
    }

}
