package net.seensin.springdockerswarmmanagementapi.modules.ssh.model.service;
import com.jcraft.jsch.JSchException;
import net.seensin.springdockerswarmmanagementapi.modules.ssh.model.service.common.SshProvider;
import net.seensin.springdockerswarmmanagementapi.modules.ssh.to.NodeNetworkCommandTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class SshClient {

    @Autowired
    SshProvider ssh;

    public List<String> nodeNetworkAccessProvider(NodeNetworkCommandTo to) throws JSchException {

        String confTxt = "[Match]\n" +"Name=eth0\n" + "\n" + "[Network]\n" + "Address="+to.getHost()+"/20 \n";
        int lastNumberOfHostIp = Integer.parseInt(to.getHost().split(Pattern.quote("."))[3]);
        for (Integer nodeId : to.getNodeIdList()) {
            confTxt = confTxt + "Address=192.168."+nodeId+"."+lastNumberOfHostIp+"/20 \n";
        }
        confTxt = confTxt + "Gateway=192.168.1.1\n";

        String command = "rm /etc/systemd/network/*;echo \"\"\"\n" +
                confTxt +
                "\"\"\" >> /etc/systemd/network/99-static-en.network;systemctl restart systemd-networkd;ls /etc/systemd/network/";
        System.out.println(command);
        return ssh.runCommand(command,to.getUser(),to.getHost(),to.getPassword());
    }

}
