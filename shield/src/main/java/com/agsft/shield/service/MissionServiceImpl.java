package com.agsft.shield.service;

import com.agsft.shield.dao.AvengerMissionAssignmentRepository;
import com.agsft.shield.dao.MissionRepository;
import com.agsft.shield.notification.email.MailSender;
import com.agsft.shield.entitiy.AvengerMissionAssignment;
import com.agsft.shield.entitiy.Avengers;
import com.agsft.shield.entitiy.Mission;
import com.agsft.shield.exception.ShieldException;
import com.agsft.shield.notification.sms.SmsSender;
import com.agsft.shield.status.MissionStatus;
import com.agsft.shield.status.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MissionServiceImpl implements MissionService {

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private SmsSender smsSender;

    @Autowired
    private AvengerMissionAssignmentService avengerMissionAssignmentService;

    @Autowired
    private AvengerMissionAssignmentRepository avengerMissionAssignmentRepository;

    @Override
    public Mission createNewMission(Mission mission) {
        if (!Objects.isNull(missionRepository.findByName(mission.getName()).orElse(null))){
            throw new ShieldException(HttpStatus.NOT_ACCEPTABLE, mission.getName(),"Mission Already Exist", "Change Mission Name !!");
        }
        mission.setStarted(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        return missionRepository.save(mission);
    }

    @Override
    public List<Mission> showAllMission() {
        return missionRepository.findAll()
                .stream()
                .map(mission -> {
                    mission.setStatus(findMissionStatus(mission));
                    return mission;
                }).collect(Collectors.toList());
    }

    @Override
    public Mission findMissionById(Long id) {
        Mission mission = missionRepository.findById(id).orElseThrow(()->new ShieldException(HttpStatus.NOT_FOUND, "missionId", "Invalid Mission Id !!", "Mission Not Found for Entered Id !!"));
        mission.setStatus(findMissionStatus(mission));
        return mission;
    }

    @Override
    public String notifyAllAboutMission(Long missionId) {
        Mission mission = findMissionById(missionId);
            if (findMissionStatus(mission).equals(MissionStatus.COMPLETED)) {
                throw new ShieldException(HttpStatus.NOT_ACCEPTABLE, "Mission Already Completed !! Try with another Mission !!");
            }
            if (mission.getAvengerMissionAssignment() == null) {
                throw new ShieldException(HttpStatus.BAD_REQUEST, "Mission Couldn't Assigned !!");
            }
            Set<AvengerMissionAssignment> sortSet = avengerMissionAssignmentService.sortAvengerMissionAssignmentSet(mission.getAvengerMissionAssignment(), false);
            sortSet.stream().forEach(avengerMissionAssignment -> {
                Avengers avengers = avengerMissionAssignment.getAvengers();
                if (avengers.getNotificationService().equals(NotificationService.SMS)) {
                    smsSender.sentSms("+91" + avengers.getContactNo(), "You Got an Mission Assignment" + "\n" + "Mission Details : \n"
                            + "Mission Name : " + mission.getName() + "\n" +
                            "Mission Start Date : " + mission.getStarted() + "\n" +
                            "Mission Status : " + mission.getStatus());
                }
                if (avengers.getNotificationService().equals(NotificationService.EMAIL)) {
                    mailSender.sendMail(avengers.getMailId(), "New Mission Assigned !!", "You Got an Mission Assignment" + "\n" + "Mission Details : \n" + "Mission Name : " + mission.getName() + "\n" +
                            "Mission Start Date : " + mission.getStarted() + "\n" +
                            "Mission Status : " + mission.getStatus());
                }
            });
            return "Mission Notification Sent !!";
    }

    @Override
    public Mission completeMission(Long missionId) {
        Mission mission = findMissionById(missionId);
        if (mission.getStatus().equals(MissionStatus.COMPLETED)) {
            throw new ShieldException(HttpStatus.ALREADY_REPORTED, "Mission Already Completed !!");
        }
        Set<AvengerMissionAssignment> avengerMissionAssignments = avengerMissionAssignmentService.sortAvengerMissionAssignmentSet(mission.getAvengerMissionAssignment(), false);
        for (AvengerMissionAssignment avengerMissionAssignment : avengerMissionAssignments) {
            avengerMissionAssignment.setDelete(true);
        }
        mission.setAvengerMissionAssignments(avengerMissionAssignments);
        mission.setCompleted(java.sql.Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        return missionRepository.save(mission);
    }

    public MissionStatus findMissionStatus(Mission mission) {
        if (Objects.isNull(mission)){
            throw new ShieldException(HttpStatus.INTERNAL_SERVER_ERROR, "Error !!");
        }
        if(!Objects.isNull(mission.getCompleted())){
            return MissionStatus.COMPLETED;
        }
        long count = avengerMissionAssignmentRepository.countByMissionAndIsDelete(mission, false);
        if (count >= 2) {
            return MissionStatus.ASSIGNED;
        }
        return MissionStatus.ARRIVED;
    }

}
