package net.seensin.springdockerswarmmanagementapi.modules.ssh.model.service;
import com.jcraft.jsch.JSchException;
import net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions.CantSetCardException;
import net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions.SshProblemException;
//import net.seensin.springdockerswarmmanagementapi.modules.ssh.model.service.common.ConfigProxy;
import net.seensin.springdockerswarmmanagementapi.modules.ssh.model.service.common.SshProvider;
import net.seensin.springdockerswarmmanagementapi.modules.ssh.to.NodeNetworkCommandTo;
import net.seensin.springdockerswarmmanagementapi.modules.ssh.to.SimpleTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class SshClient {

    @Autowired
    SshProvider ssh;

//    @Autowired
//    ConfigProxy configProxy;

    public List<String> nodeNetworkAccessProvider(NodeNetworkCommandTo to) throws JSchException, SshProblemException, CantSetCardException {

        String confTxt = "[Match]\n" +"Name=eth0\n" + "\n" + "[Network]\n" + "Address="+to.getHost()+"/20 \n";
        int lastNumberOfHostIp = Integer.parseInt(to.getHost().split(Pattern.quote("."))[3]);
        for (Integer nodeId : to.getNodeIdList()) {
            confTxt = confTxt + "Address=192.168."+nodeId+"."+lastNumberOfHostIp+"/20 \n";
        }
        confTxt = confTxt + "Gateway="+to.getGateWay()+"\n";

        String command = "rm /etc/systemd/network/*;echo \"\"\"\n" +
                confTxt +
                "\"\"\" >> /etc/systemd/network/99-static-en.network;chmod 777 /etc/systemd/network/99-static-en.network;systemctl restart systemd-networkd;ls /etc/systemd/network/";

        List<String> response = new ArrayList<>();
        try {
            response = ssh.runCommand(command,to.getUser(),to.getHost(),to.getPassword());
        }catch (Exception e){
            throw new SshProblemException();
        }

//        if (!to.getTask().getTaskName().equals(SimpleTask.SimpleTaskEnum.NULL))
//        {
//            try {
//                configProxy.executeSimpleTask(to.getTask());
//            }catch (Exception e){
//                throw new CantSetCardException();
//            }
//        }

        return response;

    }

}