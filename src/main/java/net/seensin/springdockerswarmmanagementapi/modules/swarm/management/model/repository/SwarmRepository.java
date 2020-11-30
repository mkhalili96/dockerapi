//package net.seensin.springdockerswarmmanagementapi.modules.swarm.management.model.repository;
//
//import com.github.dockerjava.api.model.SwarmJoinTokens;
//import com.github.dockerjava.api.model.SwarmSpec;
//import net.seensin.springdockerswarmmanagementapi.modules.swarm.management.to.JoinSwarmTo;
//import net.seensin.springdockerswarmmanagementapi.modules.swarm.management.to.UpdateSwarmTo;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface SwarmRepository {
//    void init(SwarmSpec swarmSpec) throws Exception;
//    void join(JoinSwarmTo joinSwarmTo) throws Exception;
//    void leave(Boolean force) throws Exception;
//    void update(UpdateSwarmTo updateSwarmTo) throws Exception;
//    SwarmJoinTokens getJoinTokens() throws Exception;
//    SwarmSpec getSwarmSpec() throws Exception;
//}
