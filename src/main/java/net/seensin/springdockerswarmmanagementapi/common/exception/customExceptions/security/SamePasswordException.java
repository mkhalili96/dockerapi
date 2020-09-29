package net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions.security;

import net.seensin.springdockerswarmmanagementapi.common.exception.ExceptionsTemplate;
import net.seensin.springdockerswarmmanagementapi.common.exception.dto.ErrorMap;

public class SamePasswordException extends ExceptionsTemplate {


    public SamePasswordException() {
        super("old Password and New Password cannot be same");
    }

    @Override
    public int getStatus() {
        return ErrorMap.SAME_PASSWORD.value();
    }

    @Override
    public ErrorMap getError() {
        return ErrorMap.SAME_PASSWORD;
    }
}
