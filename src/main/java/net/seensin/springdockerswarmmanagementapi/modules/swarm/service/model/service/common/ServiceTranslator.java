package net.seensin.springdockerswarmmanagementapi.modules.swarm.service.model.service.common;

import com.github.dockerjava.api.model.*;
import net.seensin.springdockerswarmmanagementapi.modules.swarm.service.to.ServiceTo;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceTranslator {
    public ServiceSpec Translate(ServiceTo service){
        List<NetworkAttachmentConfig> networks = new ArrayList<>();
        if (service.getNetworks() != null)
            service.getNetworks().stream().forEach(network -> networks.add(new NetworkAttachmentConfig().withTarget(network)));

        ServiceSpec spec = new ServiceSpec();
        spec.withName(service.getName());
        spec.withTaskTemplate(new TaskSpec()
                .withContainerSpec(new ContainerSpec().withImage(service.getImage()).withUser("33"))
                .withPlacement(new ServicePlacement().
                        withConstraints(service.getLabelConstraint())));
        spec.withMode(new ServiceModeConfig()
                .withReplicated(new ServiceReplicatedModeOptions()
                        .withReplicas(service.getReplicas())));
        spec.withEndpointSpec(new EndpointSpec().withPorts(service.getPorts()));
        spec.withLabels(service.getLabels());
        spec.withNetworks(networks);
        return spec;
    }
}
