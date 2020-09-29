package net.seensin.springdockerswarmmanagementapi.controller;

import com.github.dockerjava.api.model.Task;
import net.seensin.springdockerswarmmanagementapi.To.TaskSearchTo;
import net.seensin.springdockerswarmmanagementapi.model.repository.SwarmTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/swarm/tasks")
public class SwarmTaskController {

    @Autowired
    SwarmTaskRepository taskRepository;

    @GetMapping
    @PreAuthorize("hasAuthority('MONITOR')")
    public List<Task> findAllTasks(@RequestBody TaskSearchTo taskSearchTo) {
        if (taskSearchTo == null)
            taskSearchTo = new TaskSearchTo();
        return taskRepository.findAllTasks(taskSearchTo);
    }
}
