package net.seensin.springdockerswarmmanagementapi.modules.ssh.to;

import java.io.Serializable;

public  class TaskExecutionResponse implements Serializable {
    private String elementAddress;
    private String oid;
    private TaskExecutionResult.ResultType resultType;
    private String resultValue;

    public TaskExecutionResponse(String elementAddress, String oid, TaskExecutionResult.ResultType resultType, String resultValue) {
        this.elementAddress = elementAddress;
        this.oid = oid;
        this.resultType = resultType;
        this.resultValue = resultValue;
    }

    public TaskExecutionResponse() {
    }

    public String getElementAddress() {
        return elementAddress;
    }

    public TaskExecutionResponse setElementAddress(String elementAddress) {
        this.elementAddress = elementAddress;
        return this;
    }

    public String getOid() {
        return oid;
    }

    public TaskExecutionResponse setOid(String oid) {
        this.oid = oid;
        return this;
    }

    public TaskExecutionResult.ResultType getResultType() {
        return resultType;
    }

    public TaskExecutionResponse setResultType(TaskExecutionResult.ResultType resultType) {
        this.resultType = resultType;
        return this;
    }

    public String getResultValue() {
        return resultValue;
    }

    public TaskExecutionResponse setResultValue(String resultValue) {
        this.resultValue = resultValue;
        return this;
    }
}