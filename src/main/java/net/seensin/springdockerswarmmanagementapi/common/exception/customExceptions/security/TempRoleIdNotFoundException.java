package net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions.security;

import net.seensin.springdockerswarmmanagementapi.common.exception.ExceptionsTemplate;
import net.seensin.springdockerswarmmanagementapi.common.exception.dto.ErrorMap;

public class TempRoleIdNotFoundException extends ExceptionsTemplate {

    public TempRoleIdNotFoundException() {
    }

    public TempRoleIdNotFoundException(Long id) {
        super("Could not find Temporary Role with id : " + id);
    }

    public TempRoleIdNotFoundException(Long id, Throwable cause) {
        super("Could not find Temporary Role with id : " + id, cause);
    }

    @Override
    public int getStatus() {
        return ErrorMap.TEMP_ROLE_ID_NOT_FOUND.value();
    }

    @Override
    public ErrorMap getError() {
        return ErrorMap.TEMP_ROLE_ID_NOT_FOUND;
    }
}
