package net.seensin.springdockerswarmmanagementapi.modules.swarm.to;

import com.github.dockerjava.api.model.SwarmSpec;
import lombok.Data;

@Data
public class UpdateSwarmTo {
    private SwarmSpec swarmSpec;
    private Boolean rotateManagerToken = false;
    private Boolean rotateWorkerToken = false;
    private Long version;
}
