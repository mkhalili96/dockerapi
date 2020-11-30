package net.seensin.springdockerswarmmanagementapi.modules.swarm.task.to;

import com.github.dockerjava.api.model.TaskState;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class TaskSearchTo implements Serializable {
    private String id = null;
    private String name = null;
    private String service = null;
    private String node = null;
    private Map<String, String> label = null;
    private TaskState desired = null;
}
