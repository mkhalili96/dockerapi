package net.seensin.springdockerswarmmanagementapi;

import net.seensin.springdockerswarmmanagementapi.common.DockerConnectionProvider;
import net.seensin.springdockerswarmmanagementapi.model.repository.SwarmNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
public class SpringDockerSwarmManagementApiApplication {


    @Autowired
    DockerConnectionProvider docker;

    @Autowired
    SwarmNodeRepository nodeRepository;


    public static void main(String[] args) {
        SpringApplication.run(SpringDockerSwarmManagementApiApplication.class, args);
    }
}
