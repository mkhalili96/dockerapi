package net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions;


import net.seensin.springdockerswarmmanagementapi.common.exception.ExceptionsTemplate;
import net.seensin.springdockerswarmmanagementapi.common.exception.dto.ErrorMap;

import static net.seensin.springdockerswarmmanagementapi.common.exception.dto.ErrorMap.OID_EXTENSION_IS_NOT_VALID;

public class OidExtensionFormatException extends ExceptionsTemplate {
    public OidExtensionFormatException() {
        super("oidExtension is not valid");
    }

    @Override
    public int getStatus() {
        return OID_EXTENSION_IS_NOT_VALID.value();
    }

    @Override
    public ErrorMap getError() {
        return OID_EXTENSION_IS_NOT_VALID;
    }
}
