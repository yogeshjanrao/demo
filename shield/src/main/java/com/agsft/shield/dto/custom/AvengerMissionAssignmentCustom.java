package com.agsft.shield.dto.custom;

public class AvengerMissionAssignmentCustom {
    private MissionCustom mission;
    private AvengersCustom avengers;
    private boolean isDelete;
    public AvengerMissionAssignmentCustom() {
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
