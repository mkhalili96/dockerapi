package net.seensin.springdockerswarmmanagementapi.modules.ssh.to;

import java.io.Serializable;

public  class TaskExecutionResult implements Serializable {
    private Task task;
    private ResultType resultType;
    private String response;

    public TaskExecutionResult(Task task, ResultType resultType, String response) {
        this.task = task;
        this.resultType = resultType;
        this.response = response;
    }

    public TaskExecutionResult(Task task, ResultType resultType) {
        this.task = task;
        this.resultType = resultType;
        this.response = null;
    }

    public static enum ResultType {
        SUCCESSFUL,
        COULD_NOT_SET,
        COULD_NOT_CONNECT ,
        MIBVALUE_SYMBOL_NOT_FOUND ,
        OID_EXTENSION_IS_NOT_VALID ,
        OID_EXTENSION_IS_NOT_IN_SLOT_LEVEL,
        OID_EXTENSION_IS_NOT_IN_SHELF_LEVEL,
        UNHANDLED
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    public String getResponse() {
        return response;
    }

    public TaskExecutionResult setResponse(String response) {
        this.response = response;
        return this;
    }

    public TaskExecutionResult() {
    }

    public Task getTask() {
        return task;
    }

    public ResultType getResultType() {
        return resultType;
    }


}