package net.seensin.springdockerswarmmanagementapi.modules.swarm.node.to;

import lombok.Data;

import java.io.Serializable;

@Data
public class LabelTo implements Serializable {
    private String key;
    private String value;
}
