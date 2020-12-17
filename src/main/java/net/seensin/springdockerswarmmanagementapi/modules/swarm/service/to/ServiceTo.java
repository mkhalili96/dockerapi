package net.seensin.springdockerswarmmanagementapi.modules.swarm.service.to;

import com.github.dockerjava.api.model.PortConfig;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceTo implements Serializable {
    private String name;

    private String image;

    private Map<String, String> constraints;

    private int replicas = 0;

    private List<PortConfig> ports;

    private Map<String, String> labels;

    private List<String> networks;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getReplicas() {
        return replicas;
    }

    public void setReplicas(int replicas) {
        this.replicas = replicas;
    }

    public List<PortConfig> getPorts() {
        return ports;
    }

    public void setPorts(List<PortConfig> ports) {
        this.ports = ports;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    public List<String> getNetworks() {
        return networks;
    }

    public void setNetworks(List<String> networks) {
        this.networks = networks;
    }

    public ServiceTo() {
    }

    public Map<String, String> getConstraints() {
        return constraints;
    }

    public void setConstraints(Map<String, String> constraints) {
        this.constraints = constraints;
    }

    public List<String> getLabelConstraint() {
        List<String> temp = new ArrayList<>();
        if (this.constraints != null){
            for (String key : this.constraints.keySet()) {
                temp.add("node.labels."+key+"=="+this.constraints.get(key));
            }
        }
        return temp;
    }
}
