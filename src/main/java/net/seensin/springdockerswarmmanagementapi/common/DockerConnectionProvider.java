package net.seensin.springdockerswarmmanagementapi.common;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.transport.DockerHttpClient;
import net.seensin.springdockerswarmmanagementapi.common.httpclient5.ApacheDockerHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;
import java.util.*;

@Configuration
public class DockerConnectionProvider {
    @Value("${host.manager.list}")
    private List<String> hosts;


    private Map<String, DockerClient> dockerClientMap   = new HashMap();
    private Map<String, DockerHttpClient> httpClientMap = new HashMap();

    @PostConstruct
    private void init(){

        for (String ip : hosts) {
            try {
                DefaultDockerClientConfig config
                        = DefaultDockerClientConfig.createDefaultConfigBuilder()
                        .withDockerHost("tcp://"+ip+":2375").build();

                dockerClientMap.put(ip,DockerClientBuilder.getInstance(config).build());
                DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                        .dockerHost(config.getDockerHost())
                        .build();
                httpClientMap.put(ip,httpClient);

            }catch (Exception e){
                System.out.println(ip + " not valid "+e.getClass().getName());
            }
        }
    }

    public List<String> getAllNodesIp(){
        return hosts;
    }

    public DockerClient getDockerClient() {
        return dockerClientMap.get(hosts.get(0));
    }

    public DockerHttpClient getDockerHttpclient() {
        return httpClientMap.get(hosts.get(0));
    }

    public DockerClient getDockerClientByIp(String ip) {
        return dockerClientMap.get(ip);
    }

    public DockerHttpClient getDockerHttpclientByIp(String ip) {
        return httpClientMap.get(ip);
    }
}

