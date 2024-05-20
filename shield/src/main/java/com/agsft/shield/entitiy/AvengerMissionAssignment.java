package com.agsft.shield.entitiy;

import javax.persistence.*;

@Entity
@Table(name = "avengers_mission")
public class AvengerMissionAssignment extends Auditing{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avenger_id")
    private Avengers avengers;
    @Column(name = "is_delete", nullable = false)
    private boolean isDelete;

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public Avengers getAvengers() {
        return avengers;
    }

    public void setAvengers(Avengers avengers) {
        this.avengers = avengers;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }
}
