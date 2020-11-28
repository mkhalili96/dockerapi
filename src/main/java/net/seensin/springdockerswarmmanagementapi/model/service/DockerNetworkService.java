package net.seensin.springdockerswarmmanagementapi.model.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateNetworkResponse;
import com.github.dockerjava.api.model.Network;
import net.seensin.springdockerswarmmanagementapi.common.DockerConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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



}
