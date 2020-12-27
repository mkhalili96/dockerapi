package net.seensin.springdockerswarmmanagementapi.modules.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "log")
public class LogController {

    @Autowired
    MongoLogReaderService service;

    @PostMapping(value = "syslog")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Syslog>> syslogReader(@RequestBody(required = false) QueryTo query){
       return ResponseEntity.ok(service.LogReader(new BasicQuery(query.getQuery()),Syslog.class));
    }
}
