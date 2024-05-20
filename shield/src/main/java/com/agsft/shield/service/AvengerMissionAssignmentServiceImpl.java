package com.agsft.shield.service;

import com.agsft.shield.dao.AvengerMissionAssignmentRepository;
import com.agsft.shield.dao.MissionRepository;
import com.agsft.shield.entitiy.AvengerMissionAssignment;
import com.agsft.shield.entitiy.Avengers;
import com.agsft.shield.entitiy.Mission;
import com.agsft.shield.exception.ShieldException;
import com.agsft.shield.status.AvengerStatus;
import com.agsft.shield.status.MissionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AvengerMissionAssignmentServiceImpl implements AvengerMissionAssignmentService {

    @Autowired
    private AvengerMissionAssignmentRepository avengerMissionAssignmentRepository;

    @Autowired
    private AvengersService avengersService;

    @Autowired
    private MissionService missionService;

    @Autowired
    private MissionRepository missionRepository;

    @Override
    public AvengerMissionAssignment missionAssignment(Long missionId, Long avengerId) {
        AvengerMissionAssignment avengerMissionAssignment = null;
        Mission mission = missionService.findMissionById(missionId);
        Avengers avenger = avengersService.findAvengerById(avengerId);
        if (mission.getStatus().equals(MissionStatus.COMPLETED)) {
            throw new ShieldException(HttpStatus.ALREADY_REPORTED, "Mission Already Completed !!");
        }
        if (avenger.getStatus().equals(AvengerStatus.UNAVAILABLE)){
            throw new ShieldException(HttpStatus.NOT_ACCEPTABLE, "Avenger Unavailable at This Time !!");
        }
        AvengerMissionAssignment alreadyAssignedPair = avengerMissionAssignmentRepository.findByMissionAndAvengers(mission, avenger).orElse(null);
        if (alreadyAssignedPair != null && !alreadyAssignedPair.isDelete()) {
            throw new ShieldException(HttpStatus.ALREADY_REPORTED, "Mission Already Assigned to This Avenger !!");
        }
        if ((this.avengerMissionAssignmentRepository.countByMissionAndIsDelete(mission, false)) == 2) {
            throw new ShieldException(HttpStatus.ALREADY_REPORTED, "Mission Max Assignment Completed !!");
        }
        if ((this.avengerMissionAssignmentRepository.countByAvengersAndIsDelete(avenger, false)) == 2) {
            throw new ShieldException(HttpStatus.ALREADY_REPORTED, "Avenger Already Assigned on Two Mission");
        }
        avengerMissionAssignment = new AvengerMissionAssignment();
        avengerMissionAssignment.setAvengers(avenger);
        avengerMissionAssignment.setMission(mission);
        avengerMissionAssignment.setDelete(false);
        return avengerMissionAssignmentRepository.save(avengerMissionAssignment);
    }


    @Override
    public List<Avengers> findAllAvengerAssignedToMission(Long missionId) {
        Mission mission = missionService.findMissionById(missionId);
        return avengerMissionAssignmentRepository.findAvengers(mission);
    }

    @Override
    public Set<AvengerMissionAssignment> sortAvengerMissionAssignmentSet(Set<AvengerMissionAssignment> avengerMissionAssignments, boolean isDeleted) {
        if (Objects.isNull(avengerMissionAssignments) || avengerMissionAssignments.isEmpty()) {
            throw new ShieldException(HttpStatus.INTERNAL_SERVER_ERROR, "Error !!");
        }
        return avengerMissionAssignments.stream().filter(avengerMissionAssignment -> avengerMissionAssignment.isDelete() == isDeleted).collect(Collectors.toSet());
    }
}
