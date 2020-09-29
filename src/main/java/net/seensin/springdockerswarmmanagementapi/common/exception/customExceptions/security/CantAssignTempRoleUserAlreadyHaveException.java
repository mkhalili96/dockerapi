package net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions.security;

import net.seensin.springdockerswarmmanagementapi.common.exception.ExceptionsTemplate;
import net.seensin.springdockerswarmmanagementapi.common.exception.dto.ErrorMap;
public class CantAssignTempRoleUserAlreadyHaveException extends ExceptionsTemplate {


    public CantAssignTempRoleUserAlreadyHaveException() {
    }

    public CantAssignTempRoleUserAlreadyHaveException(String username, String rolename) {
        super("Could not assign [ROLE]" + rolename + " to [USER]" + username + " Temporary Roles ; user already have this role ..");
    }

    public CantAssignTempRoleUserAlreadyHaveException(String username, String rolename, Throwable cause) {
        super("Could not assign [ROLE]" + rolename + " to [USER]" + username + " Temporary Roles ; user already have this role ..", cause);
    }

    @Override
    public int getStatus() {
        return ErrorMap.CANT_ASSIGN_TEMP_ROLE_USER_ALREADY_HAVE.value();
    }

    @Override
    public ErrorMap getError() {
        return ErrorMap.CANT_ASSIGN_TEMP_ROLE_USER_ALREADY_HAVE;
    }
}
