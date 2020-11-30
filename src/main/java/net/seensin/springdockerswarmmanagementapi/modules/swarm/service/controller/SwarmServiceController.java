package net.seensin.springdockerswarmmanagementapi.modules.swarm.service.controller;

import com.github.dockerjava.api.command.CreateServiceResponse;
import com.github.dockerjava.api.model.Service;
import net.seensin.springdockerswarmmanagementapi.modules.swarm.service.model.service.SwarmServiceService;
import net.seensin.springdockerswarmmanagementapi.modules.swarm.service.to.*;
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
    public Set<PreServiceMonitorTo> preViewServices(@RequestBody(required = false) ServiceSearchTo serviceSearchTo) {
        if (serviceSearchTo == null)
            serviceSearchTo = new ServiceSearchTo();
        return swarmServiceService.findAllPreServices(serviceSearchTo);
    }

    @GetMapping(value = "/detail")
    @PreAuthorize("hasAuthority('MONITOR')")
    public Set<DetailServiceMonitorTo> detailServices(@RequestBody(required = false) ServiceSearchTo serviceSearchTo) {
        if (serviceSearchTo == null)
            serviceSearchTo = new ServiceSearchTo();
        return swarmServiceService.findAllServicesByDetail(serviceSearchTo);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public CreateServiceResponse createService(@RequestBody ServiceTo service ) {
        return swarmServiceService.createService(service);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateService(@RequestParam String id, @RequestBody ServiceTo service ) {
        return swarmServiceService.updateService(id,service);
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