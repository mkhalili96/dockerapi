package net.seensin.springdockerswarmmanagementapi.To;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReplicasTo implements Serializable {
    private String id;
    private int replicas;
}
