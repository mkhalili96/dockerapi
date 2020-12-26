package net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions;

import net.seensin.springdockerswarmmanagementapi.common.exception.ExceptionsTemplate;
import net.seensin.springdockerswarmmanagementapi.common.exception.dto.ErrorMap;

import static net.seensin.springdockerswarmmanagementapi.common.exception.dto.ErrorMap.ERROR;

public class SshProblemException extends ExceptionsTemplate {

    public SshProblemException() {
        super("cant connect to requested host");

    }
    @Override
    public int getStatus() {
        return ERROR.value();
    }

    @Override
    public ErrorMap getError() {
        return ERROR;
    }
}