package com.agsft.shield.service;

import com.agsft.shield.dao.AvengerMissionAssignmentRepository;
import com.agsft.shield.dao.AvengersRepository;
import com.agsft.shield.entitiy.Avengers;
import com.agsft.shield.entitiy.Mission;
import com.agsft.shield.exception.ShieldException;
import com.agsft.shield.status.AvengerStatus;
import com.agsft.shield.status.MissionStatus;
import com.agsft.shield.status.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AvengersServiceImpl implements AvengersService {
    @Autowired
    private AvengersRepository avengersRepository;

    @Autowired
    private AvengerMissionAssignmentRepository avengerMissionAssignmentRepository;

    @Autowired
    private MissionService missionService;

    @Override
    public Avengers createNewAvenger(Avengers avengers) {
        if (!Objects.isNull(avengersRepository.findByName(avengers.getName()).orElse(null))) {
            throw new ShieldException(HttpStatus.ALREADY_REPORTED, avengers.getName(), "Avenger Already Found !!", "Change Avenger Name !!");
        }
        return avengersRepository.save(avengers);
    }

    @Override
    public List<Avengers> viewAllAvengers() {
        List<Avengers> avengersList = avengersRepository.findAll();
        return avengersList
                .stream()
                .map(avengers -> {
                    avengers.setStatus(findAvengerStatus(avengers));
                    return avengers;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Avengers activateNotifyService(Long avengerId, String status, String contact, String mail) {
        Avengers avenger = findAvengerById(avengerId);
        if (avenger.getNotificationService() == null) {
            if (status.equalsIgnoreCase("sms") || status.equalsIgnoreCase("mail")) {
                if (status.equalsIgnoreCase("sms") && !contact.isEmpty() && !contact.isBlank()) {
                    avenger.setNotificationService(NotificationService.SMS);
                }
                avenger.setContactNo(contact);
                if (status.equalsIgnoreCase("mail") && !mail.isEmpty() && !mail.isBlank()) {
                    avenger.setNotificationService(NotificationService.EMAIL);
                }
                avenger.setMailId(mail);
                return avengersRepository.save(avenger);
            } else {
                throw new ShieldException(HttpStatus.NOT_ACCEPTABLE, "status", "Invalid Request !!", "Status must be sms either mail");
            }
        } else {
            throw new ShieldException(HttpStatus.NOT_ACCEPTABLE, "status", "Invalid Request !!", "Avenger Already Activate Service for " + avenger.getNotificationService());
        }
    }

    @Override
    public Avengers findAvengerById(Long id) {
        Avengers avengers = avengersRepository.findById(id).orElseThrow(() -> new ShieldException(HttpStatus.NOT_FOUND, "avengerId", "Invalid Avenger Id !!", "Avenger Not Found for Entered Id !!"));
        avengers.setStatus(findAvengerStatus(avengers));
        return avengers;
    }

    @Override
    public List<Mission> avengerMission(Long avengerId, String status) {
        Avengers avenger = findAvengerById(avengerId);
        List<Mission> assignedMission = new ArrayList<>();
        List<Mission> completedMission = new ArrayList<>();
        avenger.getAvengerMissionAssignments()
                .stream()
                .forEach(avengerMissionAssignment -> {
                    avengerMissionAssignment.getMission().setStatus(missionService.findMissionStatus(avengerMissionAssignment.getMission()));
                    if (status.equalsIgnoreCase("assigned")) {
                        if (avengerMissionAssignment.getMission().equals(MissionStatus.ASSIGNED)) {
                            assignedMission.add(avengerMissionAssignment.getMission());
                        }
                    }
                    if (status.equalsIgnoreCase("completed")) {
                        if (avengerMissionAssignment.getMission().getStatus().equals(MissionStatus.COMPLETED)) {
                            completedMission.add(avengerMissionAssignment.getMission());
                        }
                    }
                });
        if (status.equalsIgnoreCase("assigned")){
            return assignedMission;
        } else if (status.equalsIgnoreCase("completed")) {
            return completedMission;
        }
        return null;
    }

    public AvengerStatus findAvengerStatus(Avengers avengers) {
        if (Objects.isNull(avengers)){
            throw new ShieldException(HttpStatus.INTERNAL_SERVER_ERROR, "Error !!");
        }
        long count = avengerMissionAssignmentRepository.countByAvengersAndIsDelete(avengers, false);
        if (count >= 2) {
            return AvengerStatus.UNAVAILABLE;
        }
        return AvengerStatus.AVAILABLE;
    }

}
