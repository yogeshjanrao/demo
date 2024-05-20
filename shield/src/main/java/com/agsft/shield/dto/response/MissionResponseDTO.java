package com.agsft.shield.dto.response;

import com.agsft.shield.dto.custom.AvengerMissionAssignmentCustom;
import com.agsft.shield.status.MissionStatus;

import java.util.Date;
import java.util.Set;

public class MissionResponseDTO {
    private String name;
    private Date started;
    private Date completed;
    private MissionStatus status;
    private Set<AvengerMissionAssignmentCustom> avengerMissionAssignments;

    public String getName() {
        return name;
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

    public Set<AvengerMissionAssignmentCustom> getAvengerMissionAssignments() {
        return avengerMissionAssignments;
    }

    public void setAvengerMissionAssignments(Set<AvengerMissionAssignmentCustom> avengerMissionAssignments) {
        this.avengerMissionAssignments = avengerMissionAssignments;
    }

    @Override
    public String toString() {
        return "MissionResponseDTO{" +
                "name='" + name + '\'' +
                ", started=" + started +
                ", completed=" + completed +
                ", status=" + status +
                ", avengerMissionAssignments=" + avengerMissionAssignments +
                '}';
    }
}
