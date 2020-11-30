package net.seensin.springdockerswarmmanagementapi.modules.swarm.task.model.service;

import com.github.dockerjava.api.command.ListTasksCmd;
import com.github.dockerjava.api.model.Task;
import net.seensin.springdockerswarmmanagementapi.modules.swarm.task.to.PreViewTaskMonitorTo;
import net.seensin.springdockerswarmmanagementapi.modules.swarm.task.to.TaskSearchTo;
import net.seensin.springdockerswarmmanagementapi.common.DockerConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class SwarmTaskService {

    @Autowired
    DockerConnectionProvider connection;

    public List<Task> findAllTasks(TaskSearchTo taskSearchTo) {
        ListTasksCmd cmd = connection.getDockerClient().listTasksCmd();

        if (taskSearchTo.getId() != null)
            cmd = cmd.withIdFilter(taskSearchTo.getId());

        if (taskSearchTo.getName() != null)
            cmd = cmd.withNameFilter(taskSearchTo.getName());

        if (taskSearchTo.getService() != null)
            cmd = cmd.withServiceFilter(taskSearchTo.getService());

        if (taskSearchTo.getNode() != null)
            cmd = cmd.withNodeFilter(taskSearchTo.getNode());

        if (taskSearchTo.getDesired() != null)
            cmd = cmd.withStateFilter(taskSearchTo.getDesired());
        return cmd.exec();
    }

    public Set<PreViewTaskMonitorTo> findAllPreTasksByService(String serviceId){

        Set<PreViewTaskMonitorTo> preViewTaskMonitorTos = new HashSet<>();

        List<Task> tasks = connection.getDockerClient().listTasksCmd().withServiceFilter(serviceId).exec();
        tasks.stream().forEach(task -> {
            PreViewTaskMonitorTo preViewTaskMonitorTo
                    = new PreViewTaskMonitorTo(task.getId(),task.getServiceId(),
                    task.getNodeId(),task.getCreatedAt(),
                    task.getUpdatedAt(),task.getStatus());
            preViewTaskMonitorTos.add(preViewTaskMonitorTo);
        });
        return preViewTaskMonitorTos;
    }
}
