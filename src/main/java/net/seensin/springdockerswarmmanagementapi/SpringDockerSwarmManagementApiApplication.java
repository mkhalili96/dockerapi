package net.seensin.springdockerswarmmanagementapi;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
public class SpringDockerSwarmManagementApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDockerSwarmManagementApiApplication.class, args);

    }
//
//    @Autowired
//    MongoLogReaderService mongoLogReaderService;

//    @PostConstruct
//    public void test(){
//        Query query = new BasicQuery("{host:{$eq:\"smasaly\"}}");
//        Query q = new Query();
//
//        ((List<Syslog>) mongoLogReaderService.LogReader(query, Syslog.class)).stream().forEach(l ->{
//            System.out.println(l.getHost());
//        });
//    }

}
