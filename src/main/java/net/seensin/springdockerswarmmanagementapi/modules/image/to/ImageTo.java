package net.seensin.springdockerswarmmanagementapi.modules.image.to;

import lombok.Data;
import java.io.Serializable;
import java.util.Map;

@Data
public class ImageTo implements Serializable {
    String imageNmae;
    Map<String, String> lableMap;
    Boolean showAll;
    Boolean dangling;
}
