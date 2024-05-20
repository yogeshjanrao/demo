package com.agsft.shield.service;

import com.agsft.shield.entitiy.Mission;
import com.agsft.shield.status.MissionStatus;

import java.util.List;
import java.util.Optional;

public interface MissionService {
    Mission createNewMission(Mission mission);

    List<Mission> showAllMission();

    Mission findMissionById(Long id);

    String notifyAllAboutMission(Long missionId);

    Mission completeMission(Long missionId);

    MissionStatus findMissionStatus(Mission mission);

}
