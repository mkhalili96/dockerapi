package net.seensin.springdockerswarmmanagementapi.To;

import com.github.dockerjava.api.model.TaskStatus;
import lombok.Data;

@Data
public class PreTaskMonitorTo {
    private String taskId;
    private String serviceId;
    private String nodeId;
    private String createdAt;
    private String updatedAt;
    private TaskStatus status;

    public PreTaskMonitorTo(String taskId, String serviceId, String nodeId, String createdAt, String updatedAt, TaskStatus status) {
        this.taskId = taskId;
        this.serviceId = serviceId;
        this.nodeId = nodeId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }
}
