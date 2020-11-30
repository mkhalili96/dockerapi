package net.seensin.springdockerswarmmanagementapi.modules.swarm.task.controller;

import com.github.dockerjava.api.model.Task;
import net.seensin.springdockerswarmmanagementapi.modules.swarm.task.to.PreViewTaskMonitorTo;
import net.seensin.springdockerswarmmanagementapi.modules.swarm.task.to.TaskSearchTo;
import net.seensin.springdockerswarmmanagementapi.modules.swarm.task.model.service.SwarmTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/swarm/tasks")
public class SwarmTaskController {

    @Autowired
    SwarmTaskService taskRepository;

    @GetMapping
    @PreAuthorize("hasAuthority('MONITOR')")
    public List<Task> findAllTasks(@RequestBody TaskSearchTo taskSearchTo) {
        if (taskSearchTo == null)
            taskSearchTo = new TaskSearchTo();
        return taskRepository.findAllTasks(taskSearchTo);
    }

    @GetMapping(value = "/preview/{serviceId}")
    @PreAuthorize("hasAuthority('MONITOR')")
    public Set<PreViewTaskMonitorTo> preViewTasksByService(@PathVariable String serviceId) {
        return taskRepository.findAllPreTasksByService(serviceId);
    }
}
