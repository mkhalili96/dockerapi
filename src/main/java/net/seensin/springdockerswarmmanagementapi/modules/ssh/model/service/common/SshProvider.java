package net.seensin.springdockerswarmmanagementapi.modules.ssh.model.service.common;

import com.google.common.io.CharStreams;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions.SshProblemException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;

@Service
public class SshProvider {
    public List<String> runCommand(String command , String user , String host , String password) throws JSchException, SshProblemException {
        Session session = setupSshSession(user,host,password);
        session.connect();

        ChannelExec channel = (ChannelExec) session.openChannel("exec");
        try {
            channel.setCommand(command);
            channel.setInputStream(null);
            InputStream output = channel.getInputStream();
            channel.connect();

            String result = CharStreams.toString(new InputStreamReader(output));
            return asList(result.split("\n"));

        } catch (JSchException | IOException e) {
            closeConnection(channel, session);
            throw new SshProblemException();

        } finally {
            closeConnection(channel, session);
        }
    }

    private Session setupSshSession(String user , String host , String password) throws JSchException {
        Session session = new JSch().getSession(user, host, 22);
        session.setPassword(password);
        session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
        session.setConfig("StrictHostKeyChecking", "no"); // disable check for RSA key
        return session;
    }

    private void closeConnection(ChannelExec channel, Session session) {
        try {
            channel.disconnect();
        } catch (Exception ignored) {
        }
        session.disconnect();
    }


    public List<String> nodeNetworkAccessProvider(String host , String user , String password , List<Integer> nodeIdList) throws JSchException, SshProblemException {
        try {
            String confTxt = "[Match]\n" +"Name=eth0\n" + "\n" + "[Network]\n" + "Address="+host+"/20 \n";
            int lastNumberOfHostIp = Integer.parseInt(host.split(Pattern.quote("."))[3]);
            for (Integer nodeId : nodeIdList) {
                confTxt = confTxt + "Address=192.168."+nodeId+"."+lastNumberOfHostIp+"/20 \n";
            }
            confTxt = confTxt + "Gateway=192.168.1.1\n";

            String command = "echo \"\"\"\n" +
                    confTxt +
                    "\"\"\" >> /etc/systemd/network/99-static-en.network";
            return runCommand(command,user,host,password);
        }catch (Exception e){
            throw new SshProblemException();
        }


//        return runCommand("ls /",user,host,password);
    }


}
