package com.agsft.shield.service;

import com.agsft.shield.entitiy.AvengerMissionAssignment;
import com.agsft.shield.entitiy.Avengers;

import java.util.List;
import java.util.Set;

public interface AvengerMissionAssignmentService {
    AvengerMissionAssignment missionAssignment(Long missionId, Long avengerId);
    List<Avengers> findAllAvengerAssignedToMission(Long missionId);
    Set<AvengerMissionAssignment> sortAvengerMissionAssignmentSet(Set<AvengerMissionAssignment> avengerMissionAssignments, boolean isDeleted);
}
