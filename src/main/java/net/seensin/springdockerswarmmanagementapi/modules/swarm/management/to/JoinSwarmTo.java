package net.seensin.springdockerswarmmanagementapi.modules.swarm.management.to;

import lombok.Data;

@Data
public class JoinSwarmTo {
    private String listenAddr;
    private String advertiseAddr;
    private String joinToken;
}
