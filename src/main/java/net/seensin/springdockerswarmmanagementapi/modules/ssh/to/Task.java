package net.seensin.springdockerswarmmanagementapi.modules.ssh.to;

import java.io.Serializable;

public abstract class Task<T> implements Serializable {



//    private SimpleTaskEnum taskName;
    private OidExtension target;
    private String value;
    private SimpleTask.TaskType taskType;


//    public SimpleTaskEnum getTaskName() {
//        return taskName;
//    }


    public Task() {
    }

    public OidExtension getTarget() {
        return target;
    }

    public String getValue() {
        return value;
    }

    public SimpleTask.TaskType getTaskType() {
        return taskType;
    }

    protected Task(OidExtension target, String value, SimpleTask.TaskType taskType) {
        this.target = target;
        this.value = value;
        this.taskType = taskType;
    }

    public void setTarget(OidExtension target) {
        this.target = target;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setTaskType(SimpleTask.TaskType taskType) {
        this.taskType = taskType;
    }

    public abstract T getTaskName();
}
