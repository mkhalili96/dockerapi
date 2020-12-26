package net.seensin.springdockerswarmmanagementapi.modules.ssh.to;

import lombok.Data;

import java.util.List;

@Data
public class NodeNetworkCommandTo {
    String host ;
    String user ;
    String password ;
    List<Integer> nodeIdList;
    String gateWay;
    SimpleTask task;
}
