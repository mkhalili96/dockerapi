package net.seensin.springdockerswarmmanagementapi;

import net.seensin.springdockerswarmmanagementapi.modules.log.MongoLogReaderService;
import net.seensin.springdockerswarmmanagementapi.modules.log.Syslog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Queue;
import java.util.regex.Pattern;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
public class SpringDockerSwarmManagementApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDockerSwarmManagementApiApplication.class, args);

    }

    @Autowired
    MongoLogReaderService mongoLogReaderService;

    @PostConstruct
    public void test(){
        Query query = new BasicQuery("{host:{$eq:\"smasaly\"}}");
        Query q = new Query();

        ((List<Syslog>) mongoLogReaderService.LogReader(query, Syslog.class)).stream().forEach(l ->{
            System.out.println(l.getHost());
        });
    }

}
