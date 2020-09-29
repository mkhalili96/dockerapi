package net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions.security;

import net.seensin.springdockerswarmmanagementapi.common.exception.ExceptionsTemplate;
import net.seensin.springdockerswarmmanagementapi.common.exception.dto.ErrorMap;
public class UserRoleNotFoundException extends ExceptionsTemplate {

    public UserRoleNotFoundException() {
    }

    public UserRoleNotFoundException(String username, Long id) {
        super("Could not find Role with id : " + id + " in [User]" + username + " roles");
    }

    public UserRoleNotFoundException(String username, Long id, Throwable cause) {
        super("Could not find Role with id : " + id + " in [User]" + username + " roles", cause);
    }

    @Override
    public int getStatus() {
        return ErrorMap.USER_ROLE_NOT_FOUND.value();
    }

    @Override
    public ErrorMap getError() {
        return ErrorMap.USER_ROLE_NOT_FOUND;
    }
}
