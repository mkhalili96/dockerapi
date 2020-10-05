package net.seensin.springdockerswarmmanagementapi;

import net.seensin.springdockerswarmmanagementapi.common.DockerConnectionProvider;
import net.seensin.springdockerswarmmanagementapi.model.service.DockerRegistryService;
import net.seensin.springdockerswarmmanagementapi.model.service.SwarmNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
public class SpringDockerSwarmManagementApiApplication {


    @Autowired
    DockerConnectionProvider docker;

    @Autowired
    SwarmNodeService nodeRepository;

    @Autowired
    DockerRegistryService dockerRegistryService;

    public static void main(String[] args) {
        SpringApplication.run(SpringDockerSwarmManagementApiApplication.class, args);
    }

}
