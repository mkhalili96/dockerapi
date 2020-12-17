package net.seensin.springdockerswarmmanagementapi.modules.swarm.service.model.service;

import com.github.dockerjava.api.command.CreateServiceResponse;
import com.github.dockerjava.api.command.ListServicesCmd;
import com.github.dockerjava.api.model.*;
import net.seensin.springdockerswarmmanagementapi.modules.swarm.service.to.DetailServiceMonitorTo;
import net.seensin.springdockerswarmmanagementapi.modules.swarm.service.to.PreServiceMonitorTo;
import net.seensin.springdockerswarmmanagementapi.modules.swarm.service.to.ServiceSearchTo;
import net.seensin.springdockerswarmmanagementapi.modules.swarm.service.to.ServiceTo;
import net.seensin.springdockerswarmmanagementapi.common.DockerConnectionProvider;
import net.seensin.springdockerswarmmanagementapi.modules.swarm.service.model.service.common.ServiceTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class SwarmServiceService {

    @Autowired
    ServiceTranslator serviceTranslator;

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

    public CreateServiceResponse createService(ServiceTo service ) {
        return connection.getDockerClient().createServiceCmd(serviceTranslator.Translate(service)).exec();
    }

    public int replicas(int replicas, String id) {
        Service service = connection.getDockerClient().inspectServiceCmd(id).exec();
        service.getSpec().withMode(service.getSpec().getMode().withReplicated(service.getSpec().getMode().getReplicated().withReplicas(replicas)));
        connection.getDockerClient()
                .updateServiceCmd(id, service.getSpec())
                .withVersion(service.getVersion().getIndex())
                .withServiceId(id).withServiceSpec(service.getSpec())
                .exec();
        return replicas;
    }

    public String deleteService(String idOrName) {
        connection.getDockerClient().removeServiceCmd(idOrName).exec();
        return idOrName;
    }

    public Set<PreServiceMonitorTo> findAllPreServices(ServiceSearchTo serviceSearchTo){

        Set<PreServiceMonitorTo> preServiceMonitors = new HashSet<>();

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
        List<Service> services =cmd.exec();

        services.stream().forEach(service -> {
            PreServiceMonitorTo preServiceMonitor
                    = new PreServiceMonitorTo
                           (service.getId(),service.getSpec().getName(),
                           service.getSpec().getTaskTemplate().getContainerSpec().getImage(),
                           //todo Docker service on Global mode returns NullPointerException on this line
                           service.getSpec().getMode().getReplicated().getReplicas(),
                           service.getCreatedAt(),service.getUpdatedAt());

            List<Task> totalTasks = connection.getDockerClient().listTasksCmd().withIdFilter(service.getId()).exec();
            List<Task> runningTasks = totalTasks.stream().filter(task -> task.getStatus().getState().equals(TaskState.RUNNING)).collect(Collectors.toList());
            preServiceMonitor.setTotalInstancesCount(totalTasks.size());
            preServiceMonitor.setRunningInstancesCount(runningTasks.size());

            preServiceMonitors.add(preServiceMonitor);
        });

        return preServiceMonitors;
    }

    public Set<DetailServiceMonitorTo> findAllServicesByDetail(ServiceSearchTo serviceSearchTo){

        Set<DetailServiceMonitorTo> detailServiceMonitorTos = new HashSet<>();

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
        List<Service> services =cmd.exec();

        services.stream().forEach(service -> {
            DetailServiceMonitorTo detailServiceMonitor = new DetailServiceMonitorTo
                    (service.getId(),service.getSpec().getName(),
                            service.getSpec().getTaskTemplate().getContainerSpec().getImage(),
                            //todo Docker service on Global mode returns NullPointerException on this line
                            service.getSpec().getMode().getReplicated().getReplicas(),
                            service.getCreatedAt(),service.getUpdatedAt());

            List<Task> totalTasks = connection.getDockerClient().listTasksCmd().withIdFilter(service.getId()).exec();
            List<Task> runningTasks = totalTasks.stream().filter(task -> task.getStatus().getState().equals(TaskState.RUNNING)).collect(Collectors.toList());
            detailServiceMonitor.setTotalInstancesCount(totalTasks.size());
            detailServiceMonitor.setRunningInstancesCount(runningTasks.size());

            detailServiceMonitor.setImage(service.getSpec().getTaskTemplate().getContainerSpec().getImage());
            List<String> constraints = service
                                        .getSpec()
                                        .getTaskTemplate()
                                        .getPlacement()
                                        .getConstraints();
            if (constraints != null){
                detailServiceMonitor.setConstraints(constraints
                        .stream().map(constraint ->
                        constraint
                                .replace("node.labels.","")
                                .replace("==",":"))
                        .collect(Collectors.toSet()));
                detailServiceMonitor.setLabels(service.getSpec().getLabels());
            }


            if (service.getSpec().getNetworks() != null){
                detailServiceMonitor
                        .setNetworks(service.getSpec()
                                .getNetworks()
                                .stream()
                                .map(network -> connection.getDockerClient().inspectNetworkCmd().withNetworkId(network.getTarget()).exec().getName()).collect(Collectors.toList()));
            }

            detailServiceMonitor.setPorts(service.getSpec().getEndpointSpec().getPorts());
            detailServiceMonitorTos.add(detailServiceMonitor);
        });

        return detailServiceMonitorTos;
    }

    public String updateService(String id,ServiceTo service){
        ServiceSpec spec = serviceTranslator.Translate(service);
        connection.getDockerClient().updateServiceCmd(id ,spec)
                .withVersion(connection.getDockerClient().inspectServiceCmd(id).exec().getVersion().getIndex())
                .withServiceId(id).withServiceSpec(spec)
                .exec();
        return "ID:"+id;
    }


}
