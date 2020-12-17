package net.seensin.springdockerswarmmanagementapi.modules.ssh.model.service.common;

import com.google.common.io.CharStreams;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
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
        public List<String> runCommand(String command , String user , String host , String password) throws JSchException {
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
            throw new RuntimeException(e);

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


    public List<String> nodeNetworkAccessProvider(String host , String user , String password , List<Integer> nodeIdList) throws JSchException {
        String confTxt = "[Match]\n" +"Name=eth0\n" + "\n" + "[Network]\n" + "Address="+host+"/20 \n";
        int lastNumberOfHostIp = Integer.parseInt(host.split(Pattern.quote("."))[3]);
        for (Integer nodeId : nodeIdList) {
            confTxt = confTxt + "Address=192.168."+nodeId+"."+lastNumberOfHostIp+"/20 \n";
        }
        confTxt = confTxt + "Gateway=192.168.1.1\n";

        String command = "echo \"\"\"\n" +
                confTxt +
                "\"\"\" >> /etc/systemd/network/99-static-en.network";
        System.out.println(command);
        return runCommand(command,user,host,password);

//        return runCommand("ls /",user,host,password);
    }

    @PostConstruct
    public void test() throws JSchException {

    }

    public static void main(String[] args) throws JSchException {
        SshProvider ssh = new SshProvider();
//        System.out.println("31    "+ssh.runCommand("ls /","root" , "192.168.3.31" , "S!N@123"));
//        System.out.println("32    "+ssh.runCommand("ls /","root" , "192.168.3.32" , "S!N@123"));
//        System.out.println("33    "+ssh.runCommand("ls /","root" , "192.168.3.33" , "S!N@123"));
//        System.out.println("34    "+ssh.runCommand("ls /","root" , "192.168.3.34" , "S!N@123"));
//        System.out.println("35    "+ssh.runCommand("ls /","root" , "192.168.3.35" , "S!N@123"));
//        System.out.println("37    "+ssh.runCommand("ls /","root" , "192.168.3.37" , "S!N@123"));
//        System.out.println("39    "+ssh.runCommand("ls /","root" , "192.168.3.39" , "S!N@123"));
        List<Integer> nodes = Arrays.asList(122,133,144,11);
        System.out.println(ssh.nodeNetworkAccessProvider("192.168.3.32","root","S!N@123",nodes));
    }
}
