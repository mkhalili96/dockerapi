package net.seensin.springdockerswarmmanagementapi.To;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class NodeSearchTo implements Serializable {
    private List<String> idList = new ArrayList<>();
    private List<String> memberShipList = new ArrayList<>();
    private List<String> nameList = new ArrayList<>();
    private List<String> roleLis = new ArrayList<>();

}

