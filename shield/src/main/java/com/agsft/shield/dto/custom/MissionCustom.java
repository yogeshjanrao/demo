package com.agsft.shield.dto.custom;

import com.agsft.shield.status.MissionStatus;

import java.util.Date;

public class MissionCustom {
    private String name;
    private Date started;
    private Date completed;
    private MissionStatus status;

    public String getName() {        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStarted() {
        return started;
    }

    public void setStarted(Date started) {
        this.started = started;
    }

    public Date getCompleted() {
        return completed;
    }

    public void setCompleted(Date completed) {
        this.completed = completed;
    }

    public MissionStatus getStatus() {
        return status;
    }

    public void setStatus(MissionStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MissionCustom{" +
                "name='" + name + '\'' +
                ", started=" + started +
                ", completed=" + completed +
                ", status=" + status +
                '}';
    }
}
