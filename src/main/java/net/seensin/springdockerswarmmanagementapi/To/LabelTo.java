package net.seensin.springdockerswarmmanagementapi.To;

import lombok.Data;

import java.io.Serializable;

@Data
public class LabelTo implements Serializable {
    private String key;
    private String value;
}
