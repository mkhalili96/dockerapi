package net.seensin.springdockerswarmmanagementapi.modules.network.controller;

import com.github.dockerjava.api.command.CreateNetworkResponse;
import com.github.dockerjava.api.model.Network;
import net.seensin.springdockerswarmmanagementapi.modules.network.model.service.DockerNetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/network")
public class DockerNetworkController {

    @Autowired
    DockerNetworkService service;


    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CreateNetworkResponse> createNetwork(@RequestParam String name) throws Exception {
        return ResponseEntity.ok(service.createOverlayNetwork(name));
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Network>> getAllNetworks() throws Exception {
        return ResponseEntity.ok(service.getOverlayNetworks());
    }

}
