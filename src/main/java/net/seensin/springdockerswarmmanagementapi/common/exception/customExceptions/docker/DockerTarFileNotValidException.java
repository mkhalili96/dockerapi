package net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions.docker;

import net.seensin.springdockerswarmmanagementapi.common.exception.ExceptionsTemplate;
import net.seensin.springdockerswarmmanagementapi.common.exception.dto.ErrorMap;

public class DockerTarFileNotValidException  extends ExceptionsTemplate {


    public DockerTarFileNotValidException() {
        super("uploaded file is not a valid docker tar file");
    }
    @Override
    public int getStatus() {
        return ErrorMap.DOCKER_TAR_FILE_NOT_VALID.value();
    }

    @Override
    public ErrorMap getError() {
        return ErrorMap.DOCKER_TAR_FILE_NOT_VALID;
    }
}