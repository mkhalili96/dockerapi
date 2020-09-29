package net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions.security;

import net.seensin.springdockerswarmmanagementapi.common.exception.ExceptionsTemplate;
import net.seensin.springdockerswarmmanagementapi.common.exception.dto.ErrorMap;
public class ClientVersionHeaderException extends ExceptionsTemplate {


    public ClientVersionHeaderException() {
        super("please specify client-version in header");
    }

    @Override
    public int getStatus() {
        return ErrorMap.CLIENT_VERSION_HEADER.value();
    }

    @Override
    public ErrorMap getError() {
        return ErrorMap.CLIENT_VERSION_HEADER;
    }
}
