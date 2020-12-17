package net.seensin.springdockerswarmmanagementapi.modules.network.model.service;

import com.github.dockerjava.api.command.CreateNetworkResponse;
import com.github.dockerjava.api.model.Network;
import net.seensin.springdockerswarmmanagementapi.common.DockerConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DockerNetworkService {

    @Autowired
    DockerConnectionProvider connection;

    public CreateNetworkResponse createOverlayNetwork(String networkName){
        return connection.getDockerClient().createNetworkCmd().withDriver("overlay").withName(networkName).exec();
    }

    public List<Network> getOverlayNetworks(){
        return connection.getDockerClient().listNetworksCmd().exec();
    }

    public List<String> getOverlayNetworkNames(){
        return connection.getDockerClient().listNetworksCmd().exec().stream().map(network -> network.getName()).collect(Collectors.toList());
    }

    public String getNetworkById(String id){
        return connection.getDockerClient().inspectNetworkCmd().withNetworkId(id).exec().getName();
    }



}
