package net.seensin.springdockerswarmmanagementapi.modules.log;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Data
@Document(collection="LOG")
public class Syslog {

    @Id
    String _id;
    String host;
    String ident;
    String pid;
    String message;
    String priority;
    String facilityLevel;
    String hostsource;
    String time;

}
