package net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions;

import net.seensin.springdockerswarmmanagementapi.common.exception.ExceptionsTemplate;
import net.seensin.springdockerswarmmanagementapi.common.exception.dto.ErrorMap;

import static net.seensin.springdockerswarmmanagementapi.common.exception.dto.ErrorMap.ERROR;

public class CantSetCardException  extends ExceptionsTemplate {
    public CantSetCardException() {
        super("cant set card configuration");
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
