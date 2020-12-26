package net.seensin.springdockerswarmmanagementapi.modules.ssh.controller;

import com.jcraft.jsch.JSchException;
import net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions.CantSetCardException;
import net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions.SshProblemException;
import net.seensin.springdockerswarmmanagementapi.modules.ssh.model.service.SshClient;
import net.seensin.springdockerswarmmanagementapi.modules.ssh.to.NodeNetworkCommandTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/ssh")
public class SshController {

    @Autowired
    SshClient service;

    @PostMapping(value = "/node-network")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map> configNodeNetwork(@RequestBody NodeNetworkCommandTo command) throws JSchException, SshProblemException, CantSetCardException {
        Map map = new HashMap();
        map.put("response",service.nodeNetworkAccessProvider(command));
        return ResponseEntity.ok(map);
    }
}
