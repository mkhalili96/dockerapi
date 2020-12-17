package net.seensin.springdockerswarmmanagementapi.modules.swarm.node.model.service;

import com.github.dockerjava.api.command.ListSwarmNodesCmd;
import com.github.dockerjava.api.model.ObjectVersion;
import com.github.dockerjava.api.model.SwarmNode;
import com.github.dockerjava.api.model.SwarmNodeSpec;
import net.seensin.springdockerswarmmanagementapi.modules.swarm.node.to.LabelTo;
import net.seensin.springdockerswarmmanagementapi.modules.swarm.node.to.NodeSearchTo;
import net.seensin.springdockerswarmmanagementapi.common.DockerConnectionProvider;
import net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions.docker.SwarmNodeIdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SwarmNodeService {

    @Autowired
    DockerConnectionProvider connection;

    public List<SwarmNode> getAllNodes(NodeSearchTo nodeSearchTo) throws IOException {
        ListSwarmNodesCmd cmd = connection.getDockerClient().listSwarmNodesCmd();
        if (nodeSearchTo.getIdList() != null) {
            cmd = cmd.withIdFilter(nodeSearchTo.getIdList());
        }
        if (nodeSearchTo.getMemberShipList().isEmpty()) {
            cmd = cmd.withMembershipFilter(nodeSearchTo.getMemberShipList());
        }
        if (nodeSearchTo.getNameList() != null) {
            cmd = cmd.withNameFilter(nodeSearchTo.getNameList());
        }
        if (nodeSearchTo.getRoleLis() != null) {
            cmd = cmd.withRoleFilter(nodeSearchTo.getRoleLis());
        }
        return cmd.exec();
    }


    public List<SwarmNode> getAllNodesByLabelKeyValue(LabelTo labelTo) {
        return connection.getDockerClient().listSwarmNodesCmd().exec()
                .stream()
                .filter(swarmNode ->
                        swarmNode.getSpec().labels.containsKey(labelTo.getKey()) && swarmNode.getSpec().labels.get(labelTo.getKey()).equals(labelTo.getValue())
                ).collect(Collectors.toList());
    }

    public List<SwarmNode> getAllNodesByLabel(LabelTo labelTo) {
        return connection.getDockerClient().listSwarmNodesCmd().exec()
                .stream()
                .filter(swarmNode ->
                        swarmNode.getSpec().labels.containsKey(labelTo.getKey())
                ).collect(Collectors.toList());
    }

    //todo test this method
    public ObjectVersion getNodeLastVersionById(String id) {
        SwarmNode node = connection.getDockerClient().listSwarmNodesCmd().withIdFilter(Arrays.asList(id)).exec().stream().findFirst().orElseThrow (SwarmNodeIdNotFoundException::new);
        return node.getVersion();
    }

//    public DockerHttpClient.Response removeNode(String id){
//
//        DockerHttpClient.Request request = DockerHttpClient.Request.builder()
//                .method(DockerHttpClient.Request.Method.GET)
//                .path("/_ping")
//                .build();
//
//        return connection.getDockerHttpclient().execute(request);
//    }

    public String updateNode(String id, Long version, SwarmNodeSpec spec) {
        connection.getDockerClient().updateSwarmNodeCmd().withSwarmNodeId(id).withVersion(version).withSwarmNodeSpec(spec).exec();
        return id;
    }

        public List<String> getAllManagerNodesIp(){
        return connection.getAllNodesIp();
    }


}
