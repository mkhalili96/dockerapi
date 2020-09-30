package net.seensin.springdockerswarmmanagementapi.controller;

import com.github.dockerjava.api.command.CreateServiceResponse;
import com.github.dockerjava.api.model.Service;
import com.github.dockerjava.api.model.ServiceSpec;
import net.seensin.springdockerswarmmanagementapi.To.PreServiceMonitorTo;
import net.seensin.springdockerswarmmanagementapi.To.ReplicasTo;
import net.seensin.springdockerswarmmanagementapi.To.ServiceSearchTo;
import net.seensin.springdockerswarmmanagementapi.model.repository.SwarmServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/swarm/services")
public class SwarmServiceController {

    @Autowired
    SwarmServiceRepository swarmServiceRepository;

    @GetMapping
    @PreAuthorize("hasAuthority('MONITOR')")
    public List<Service> findAllServices(@RequestBody(required = false) ServiceSearchTo serviceSearchTo) {
        if (serviceSearchTo == null)
            serviceSearchTo = new ServiceSearchTo();
        return swarmServiceRepository.findAllServices(serviceSearchTo);
    }

    @GetMapping(value = "/preview")
    @PreAuthorize("hasAuthority('MONITOR')")
    public Set<PreServiceMonitorTo> preViewServices() {
        return swarmServiceRepository.findAllPreServices();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public CreateServiceResponse createService(@RequestBody ServiceSpec serviceSpec) {
        return swarmServiceRepository.createService(serviceSpec);
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public int replicas(@RequestBody ReplicasTo replicasTo) {
        return swarmServiceRepository.replicas(replicasTo.getReplicas(), replicasTo.getId());
    }

    @DeleteMapping(path = "/{idOrName}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteService(@PathVariable String idOrName) {
        return swarmServiceRepository.deleteService(idOrName);
    }


}