package net.seensin.springdockerswarmmanagementapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.regex.Pattern;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
public class SpringDockerSwarmManagementApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDockerSwarmManagementApiApplication.class, args);

    }

}
