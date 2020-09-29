package net.seensin.springdockerswarmmanagementapi.model.repository;

import com.github.dockerjava.api.command.ListTasksCmd;
import com.github.dockerjava.api.model.Task;
import net.seensin.springdockerswarmmanagementapi.To.TaskSearchTo;
import net.seensin.springdockerswarmmanagementapi.common.DockerConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SwarmTaskRepository {

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
}
