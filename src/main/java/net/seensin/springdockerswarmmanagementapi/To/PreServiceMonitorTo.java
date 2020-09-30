package net.seensin.springdockerswarmmanagementapi.To;

import lombok.Data;

import java.util.Date;

@Data
public class PreServiceMonitorTo {

    private String serviceId;

    private String serviceName;

    private String imageName;

    private Long replicas;

    private Integer totalInstancesCount;

    private Integer runningInstancesCount;

    private Date createdAt;

    private Date updatedAt;


    public PreServiceMonitorTo(String serviceId, String serviceName, String imageName, Long replicas, Date createdAt, Date updatedAt) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.imageName = imageName;
        this.replicas = replicas;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
