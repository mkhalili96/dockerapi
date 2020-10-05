package net.seensin.springdockerswarmmanagementapi.controller;

import com.github.dockerjava.api.command.CreateServiceResponse;
import com.github.dockerjava.api.model.Service;
import com.github.dockerjava.api.model.ServiceSpec;
import net.seensin.springdockerswarmmanagementapi.To.PreServiceMonitorTo;
import net.seensin.springdockerswarmmanagementapi.To.ReplicasTo;
import net.seensin.springdockerswarmmanagementapi.To.ServiceSearchTo;
import net.seensin.springdockerswarmmanagementapi.model.service.SwarmServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/swarm/services")
public class SwarmServiceController {

    @Autowired
    SwarmServiceService swarmServiceService;

    @GetMapping
    @PreAuthorize("hasAuthority('MONITOR')")
    public List<Service> findAllServices(@RequestBody(required = false) ServiceSearchTo serviceSearchTo) {
        if (serviceSearchTo == null)
            serviceSearchTo = new ServiceSearchTo();
        return swarmServiceService.findAllServices(serviceSearchTo);
    }

    @GetMapping(value = "/preview")
    @PreAuthorize("hasAuthority('MONITOR')")
    public Set<PreServiceMonitorTo> preViewServices() {
        return swarmServiceService.findAllPreServices();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public CreateServiceResponse createService(@RequestBody ServiceSpec serviceSpec) {
        return swarmServiceService.createService(serviceSpec);
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public int replicas(@RequestBody ReplicasTo replicasTo) {
        return swarmServiceService.replicas(replicasTo.getReplicas(), replicasTo.getId());
    }

    @DeleteMapping(path = "/{idOrName}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteService(@PathVariable String idOrName) {
        return swarmServiceService.deleteService(idOrName);
    }

}