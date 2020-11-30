package net.seensin.springdockerswarmmanagementapi.modules.image.to;

import lombok.Data;

import java.io.Serializable;

@Data
public class CloneTo implements Serializable {
    private String imageName;
    private String newNmae;
    private String tag;
}
