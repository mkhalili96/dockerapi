//package net.seensin.springdockerswarmmanagementapi.model.service;
//
//import com.github.dockerjava.api.model.Swarm;
//import net.seensin.springdockerswarmmanagementapi.common.DockerConnectionProvider;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//
//
//@Service
//public class SwarmNodeService {
//
//    @Autowired
//    DockerConnectionProvider connectionProvider;
//
//    public Swarm nodeInspect(){
//       return connectionProvider.getDockerClient().inspectSwarmCmd().exec();
//    }
//
//    public String changeNodeAvailability(){
//        connectionProvider.getDockerClient().updateSwarmNodeCmd().
//    }
//}
