package net.seensin.springdockerswarmmanagementapi.common;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.transport.DockerHttpClient;
import net.seensin.springdockerswarmmanagementapi.common.httpclient5.ApacheDockerHttpClient;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DockerConnectionProvider {
    private DefaultDockerClientConfig config
            = DefaultDockerClientConfig.createDefaultConfigBuilder()
//            .withRegistryEmail("info@baeldung.com")
//            .withRegistryPassword("baeldung")
//            .withRegistryUsername("baeldung")
//            .withDockerCertPath("/home/baeldung/.docker/certs")
//            .withDockerConfig("/home/baeldung/.docker/")
//            .withDockerTlsVerify("1")
            .withDockerHost("tcp://192.168.3.31:2375").build();

    private DockerClient dockerClient = DockerClientBuilder.getInstance(config).build();


    public DockerClient getDockerClient() {
        return dockerClient;
    }

    public DockerHttpClient getDockerHttpclient() {
        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .build();
        return httpClient;
    }


}

