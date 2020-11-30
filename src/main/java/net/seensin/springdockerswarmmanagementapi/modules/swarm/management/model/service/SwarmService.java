//package net.seensin.springdockerswarmmanagementapi.modules.swarm.management.model.service;
//
//import com.github.dockerjava.api.model.SwarmJoinTokens;
//import com.github.dockerjava.api.model.SwarmSpec;
//import net.seensin.springdockerswarmmanagementapi.modules.swarm.management.model.repository.SwarmRepository;
//import net.seensin.springdockerswarmmanagementapi.modules.swarm.management.to.JoinSwarmTo;
//import net.seensin.springdockerswarmmanagementapi.modules.swarm.management.to.UpdateSwarmTo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class SwarmService {
//
//    @Autowired
//    SwarmRepository swarmRepository;
//
//    public SwarmJoinTokens initSwarm(SwarmSpec swarmSpec) throws Exception {
//        swarmRepository.init(swarmSpec);
//        return swarmRepository.getJoinTokens();
//    }
//
//    public void joinSwarm(JoinSwarmTo joinSwarmTo) throws Exception {
//        swarmRepository.join(joinSwarmTo);
//    }
//
//    public void leaveSwarm(Boolean force) throws Exception {
//        swarmRepository.leave(force);
//    }
//
//    public SwarmSpec update(UpdateSwarmTo updateSwarmTo) throws Exception {
//        swarmRepository.update(updateSwarmTo);
//        return swarmRepository.getSwarmSpec();
//    }
//
//    public SwarmJoinTokens getJoinTokens() throws Exception {
//        return swarmRepository.getJoinTokens();
//    }
//}
