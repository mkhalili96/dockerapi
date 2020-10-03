package net.seensin.springdockerswarmmanagementapi.model.service;

import net.seensin.springdockerswarmmanagementapi.common.DockerConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DockerRegistryService {

    @Autowired
    DockerConnectionProvider connection;
    public void pullImage(){
//        System.out.println("hey");
//        connection.getDockerClient().buildImageCmd()
//        connection.getDockerClient().tagImageCmd("docker-api","localhost:5000/api",null).exec();
    }

}
