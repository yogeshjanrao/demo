package com.agsft.shield.entitiy;

import com.agsft.shield.status.MissionStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "mission")
public class Mission extends Auditing {
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "start_date_time")
    private Date started;
    @Column(name = "end_date_time")
    private Date completed;
    @Transient
    private MissionStatus status;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "mission")
    private Set<AvengerMissionAssignment> avengerMissionAssignments;

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

    public Set<AvengerMissionAssignment> getAvengerMissionAssignment() {
        return avengerMissionAssignments;
    }

    public void setAvengerMissionAssignments(Set<AvengerMissionAssignment> avengerMissionAssignments) {
        this.avengerMissionAssignments = avengerMissionAssignments;
    }
}
