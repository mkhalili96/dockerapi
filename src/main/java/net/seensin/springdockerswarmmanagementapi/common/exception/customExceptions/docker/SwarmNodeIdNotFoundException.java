package net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions.docker;

import com.github.dockerjava.api.exception.DockerException;

public class SwarmNodeIdNotFoundException extends DockerException {

    public SwarmNodeIdNotFoundException() {
        super("Node Id Not Found", 404);
    }

}
