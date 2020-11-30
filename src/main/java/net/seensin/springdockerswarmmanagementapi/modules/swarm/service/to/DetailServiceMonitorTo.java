package net.seensin.springdockerswarmmanagementapi.modules.swarm.service.to;

import com.github.dockerjava.api.model.PortConfig;
import lombok.Data;

import java.util.*;

@Data
public class DetailServiceMonitorTo extends PreServiceMonitorTo {

    private String image;

    private Set<String> constraints = new HashSet<>();

    private List<PortConfig> ports = new ArrayList<>();

    private Map<String, String> labels =  new HashMap<>();

    private List<String> networks = new ArrayList<>();

    public DetailServiceMonitorTo(String serviceId, String serviceName, String imageName, Long replicas, Date createdAt, Date updatedAt) {
        super(serviceId, serviceName, imageName, replicas, createdAt, updatedAt);
    }

    public DetailServiceMonitorTo() {
    }
}
