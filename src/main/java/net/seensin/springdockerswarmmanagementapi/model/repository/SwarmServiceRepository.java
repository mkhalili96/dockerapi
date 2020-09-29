package net.seensin.springdockerswarmmanagementapi.model.repository;

import com.github.dockerjava.api.command.CreateServiceResponse;
import com.github.dockerjava.api.command.ListServicesCmd;
import com.github.dockerjava.api.model.Service;
import com.github.dockerjava.api.model.ServiceSpec;
import net.seensin.springdockerswarmmanagementapi.To.ServiceSearchTo;
import net.seensin.springdockerswarmmanagementapi.common.DockerConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SwarmServiceRepository {

    @Autowired
    DockerConnectionProvider connection;

    public List<Service> findAllServices(ServiceSearchTo serviceSearchTo) {
        ListServicesCmd cmd = connection.getDockerClient().listServicesCmd();

        if (serviceSearchTo.getId() != null) {
            cmd = cmd.withIdFilter(serviceSearchTo.getId());
        }
        if (serviceSearchTo.getName() != null) {
            cmd = cmd.withNameFilter(serviceSearchTo.getName());
        }
        if (serviceSearchTo.getLabel() != null) {
            cmd = cmd.withLabelFilter(serviceSearchTo.getLabel());
        }
        return cmd.exec();
    }

    public CreateServiceResponse createService(ServiceSpec serviceSpec) {
        return connection.getDockerClient().createServiceCmd(serviceSpec).exec();
    }

    public int replicas(int replicas, String id) {
        Service service = connection.getDockerClient().inspectServiceCmd(id).exec();
        service.getSpec().withMode(service.getSpec().getMode().withReplicated(service.getSpec().getMode().getReplicated().withReplicas(replicas)));
//        spec.withLabels(updateTo.getLabels());
        connection.getDockerClient().updateServiceCmd(id, service.getSpec()).withVersion(service.getVersion().getIndex()).withServiceId(id).withServiceSpec(service.getSpec()).exec();
        return replicas;
    }

    public String deleteService(String idOrName) {
        connection.getDockerClient().removeServiceCmd(idOrName).exec();
        return idOrName;
    }
}
