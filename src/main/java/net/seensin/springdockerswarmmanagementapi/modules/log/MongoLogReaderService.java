package net.seensin.springdockerswarmmanagementapi.modules.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MongoLogReaderService {

    @Autowired
    MongoTemplate template;

   public List LogReader(Query query , Class clazz){

       return template.find(query,clazz);

   }

}
