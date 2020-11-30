package net.seensin.springdockerswarmmanagementapi.modules.swarm.service.to;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReplicasTo implements Serializable {
    private String id;
    private int replicas;
}
