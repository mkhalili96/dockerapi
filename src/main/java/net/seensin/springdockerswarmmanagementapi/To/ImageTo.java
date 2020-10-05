package net.seensin.springdockerswarmmanagementapi.To;

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
