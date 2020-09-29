package net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions.security;


import net.seensin.springdockerswarmmanagementapi.common.exception.ExceptionsTemplate;
import net.seensin.springdockerswarmmanagementapi.common.exception.dto.ErrorMap;

public class BearerException extends ExceptionsTemplate {


    public BearerException() {
        super("JWT Token does not begin with Bearer String");
    }
    @Override
    public int getStatus() {
        return ErrorMap.Unauthorized.value();
    }

    @Override
    public ErrorMap getError() {
        return ErrorMap.Unauthorized;
    }
}
