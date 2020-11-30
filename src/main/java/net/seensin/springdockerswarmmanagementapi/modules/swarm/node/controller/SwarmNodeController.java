package net.seensin.springdockerswarmmanagementapi.modules.swarm.node.controller;

import com.github.dockerjava.api.model.ObjectVersion;
import com.github.dockerjava.api.model.SwarmNode;
import com.github.dockerjava.api.model.SwarmNodeSpec;
import net.seensin.springdockerswarmmanagementapi.modules.swarm.node.to.LabelTo;
import net.seensin.springdockerswarmmanagementapi.modules.swarm.node.to.NodeSearchTo;
import net.seensin.springdockerswarmmanagementapi.modules.swarm.node.model.service.SwarmNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController()
@RequestMapping("/swarm/nodes")
public class SwarmNodeController {

    @Autowired
    SwarmNodeService nodeRepository;

    @GetMapping
    @PreAuthorize("hasAuthority('MONITOR')")
    public List<SwarmNode> getAllNodes(@RequestBody(required = false) NodeSearchTo nodeSearchTo) throws IOException {
        if (nodeSearchTo == null)
            nodeSearchTo = new NodeSearchTo();
        return nodeRepository.getAllNodes(nodeSearchTo);
    }

    @GetMapping(path = "/label")
    @PreAuthorize("hasAuthority('MONITOR')")
    public List<SwarmNode> getAllNodesByLabelKeyValue(@RequestBody LabelTo labelTo) throws IOException {
        if (labelTo.getValue() != null)
            return nodeRepository.getAllNodesByLabelKeyValue(labelTo);
        else
            return nodeRepository.getAllNodesByLabel(labelTo);
    }


    @GetMapping(path = "/lastversion/{id}")
    @PreAuthorize("hasAuthority('MONITOR')")
    public ObjectVersion getNodeLastVersionById(@PathVariable String id) throws IOException {
        return nodeRepository.getNodeLastVersionById(id);
    }

    @PostMapping(path = "/{id}/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateNodeById(@PathVariable String id, @RequestParam Long version, @RequestBody SwarmNodeSpec nodeSpec) throws IOException {
        return nodeRepository.updateNode(id, version, nodeSpec);
    }

    @PostMapping(path = "/managers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<String> getAllManagerNodesIp() {
        return nodeRepository.getAllManagerNodesIp();
    }

}
