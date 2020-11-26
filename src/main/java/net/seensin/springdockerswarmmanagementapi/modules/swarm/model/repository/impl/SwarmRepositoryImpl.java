//package net.seensin.springdockerswarmmanagementapi.modules.swarm.model.repository.impl;
//
//import com.github.dockerjava.api.DockerClient;
//import com.github.dockerjava.api.model.SwarmJoinTokens;
//import com.github.dockerjava.api.model.SwarmSpec;
//import net.seensin.springdockerswarmmanagementapi.modules.swarm.model.repository.SwarmRepository;
//import net.seensin.springdockerswarmmanagementapi.modules.swarm.to.JoinSwarmTo;
//import net.seensin.springdockerswarmmanagementapi.modules.swarm.to.UpdateSwarmTo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class SwarmRepositoryImpl implements SwarmRepository {
//
//    @Autowired
//    DockerClient dockerClient;
//
//    @Override
//    public void init(SwarmSpec swarmSpec) throws Exception {
//        dockerClient.initializeSwarmCmd(swarmSpec).exec();
//    }
//
//    @Override
//    public void join(JoinSwarmTo joinSwarmTo) throws Exception {
//        dockerClient.joinSwarmCmd()
//                .withJoinToken(joinSwarmTo.getJoinToken())
//                .withAdvertiseAddr(joinSwarmTo.getAdvertiseAddr())
//                .withListenAddr(joinSwarmTo.getListenAddr())
//                .exec();
//    }
//
//    @Override
//    public void leave(Boolean force) throws Exception {
//        dockerClient.leaveSwarmCmd().withForceEnabled(force).exec();
//    }
//
//    @Override
//    public void update(UpdateSwarmTo updateSwarmTo) throws Exception {
//        dockerClient.updateSwarmCmd(updateSwarmTo.getSwarmSpec())
//                .withRotateManagerToken(updateSwarmTo.getRotateManagerToken())
//                .withRotateWorkerToken(updateSwarmTo.getRotateWorkerToken())
//                .withVersion(updateSwarmTo.getVersion())
//                .exec();
//    }
//
//    @Override
//    public SwarmJoinTokens getJoinTokens() throws Exception {
//       return dockerClient.inspectSwarmCmd().exec().getJoinTokens();
//    }
//
//    @Override
//    public SwarmSpec getSwarmSpec() throws Exception {
//        return dockerClient.inspectSwarmCmd().exec().getSpec();
//    }
//}
