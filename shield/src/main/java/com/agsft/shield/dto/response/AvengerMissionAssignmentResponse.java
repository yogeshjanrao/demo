package com.agsft.shield.dto.response;

import com.agsft.shield.dto.custom.AvengersCustom;
import com.agsft.shield.dto.custom.MissionCustom;

public class AvengerMissionAssignmentResponse {
    private MissionCustom mission;
    private AvengersCustom avengers;
    private boolean isDelete;
    public AvengerMissionAssignmentResponse() {
    }

    public MissionCustom getMission() {
        return mission;
    }

    public void setMission(MissionCustom mission) {
        this.mission = mission;
    }

    public AvengersCustom getAvengers() {
        return avengers;
    }

    public void setAvengers(AvengersCustom avengers) {
        this.avengers = avengers;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    @Override
    public String toString() {
        return "AvengerMissionAssignmentCustom{" +
                "mission=" + mission +
                ", avengers=" + avengers +
                ", isDelete=" + isDelete +
                '}';
    }
}
