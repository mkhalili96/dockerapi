package net.seensin.springdockerswarmmanagementapi.modules.swarm.service.to;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ServiceSearchTo implements Serializable {
    private List<String> id = new ArrayList<>();
    private List<String> name = new ArrayList<>();
    private Map<String, String> label = new HashMap<>();
}
