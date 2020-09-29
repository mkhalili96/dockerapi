package net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions.security;


import net.seensin.springdockerswarmmanagementapi.common.exception.ExceptionsTemplate;
import net.seensin.springdockerswarmmanagementapi.common.exception.dto.ErrorMap;

public class UnauthorizedException extends ExceptionsTemplate {


    public UnauthorizedException() {
        super("Unauthorized Access");
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
