package com.agsft.shield.controller;

import com.agsft.shield.dto.ResponseDTO;
import com.agsft.shield.dto.response.AvengerMissionAssignmentResponse;
import com.agsft.shield.dto.response.AvengersResponseDTO;
import com.agsft.shield.notification.email.MailSender;
import com.agsft.shield.entitiy.AvengerMissionAssignment;
import com.agsft.shield.entitiy.Avengers;
import com.agsft.shield.exception.ShieldException;
import com.agsft.shield.service.AvengerMissionAssignmentService;
import com.agsft.shield.notification.sms.SmsSender;
import com.agsft.shield.status.NotificationService;
import com.agsft.shield.util.MappingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "shield/mission/assignment")
public class AvengerMissionAssignmentController {

    @Autowired
    private AvengerMissionAssignmentService avengerMissionAssignmentService;
    @Autowired
    private MappingUtil mappingUtil;
    @Autowired
    private MailSender mailSender;
    @Autowired
    private SmsSender smsSender;

    @RequestMapping(value = "/{missionId}/assign/{avengerId}", method = RequestMethod.POST)
    //Admin
    public ResponseEntity<?> assignMissionToAvenger(@PathVariable("missionId") Long missionId, @PathVariable("avengerId") Long avengerId) {
        AvengerMissionAssignment avengerMissionAssignment = avengerMissionAssignmentService.missionAssignment(missionId, avengerId);
        if (avengerMissionAssignment != null) {
            AvengerMissionAssignmentResponse avengerMissionAssignmentResponse = mappingUtil.entityToResponseDTO(avengerMissionAssignment);
            if (avengerMissionAssignmentResponse.getAvengers().getNotificationService().equals(NotificationService.SMS)) {
                smsSender.sentSms("+91" + avengerMissionAssignmentResponse.getAvengers().getContactNo(),
                        "You Got an Mission Assignment" + "\n" + "Mission Details : \n" +
                                "Mission Name : " + avengerMissionAssignmentResponse.getMission().getName() + "\n" +
                                "Mission Start Date : " + avengerMissionAssignmentResponse.getMission().getStarted() + "\n" +
                                "Mission Status : " + avengerMissionAssignmentResponse.getMission().getStatus());
            }
            if (avengerMissionAssignmentResponse.getAvengers().getNotificationService().equals(NotificationService.EMAIL)) {
                mailSender.sendMail(avengerMissionAssignmentResponse.getAvengers().getMailId(), "New Mission Assigned !!", "You Got an Mission Assignment" + "\n" +
                        "Mission Details : \n" + "Mission Details : \n"
                        + "Mission Name : " + avengerMissionAssignmentResponse.getMission().getName() + "\n" +
                        "Mission Start Date : " + avengerMissionAssignmentResponse.getMission().getStarted() + "\n" +
                        "Mission Status : " + avengerMissionAssignmentResponse.getMission().getStatus());
            }
            return ResponseEntity.ok(new ResponseDTO(200, avengerMissionAssignmentResponse, "OK"));
        }
        throw new ShieldException(HttpStatus.BAD_REQUEST, "Error !!");
    }

    @RequestMapping(value = "/find/avengers/{missionId}", method = RequestMethod.POST)
    //Admin
    public ResponseEntity<?> findAvengersByMissionId(@PathVariable("missionId") Long missionId) {
        List<Avengers> avengersList = avengerMissionAssignmentService.findAllAvengerAssignedToMission(missionId);
        if (!avengersList.isEmpty()){
            List<AvengersResponseDTO> avengersResponseDTOS = avengersList.stream().map(avengers-> mappingUtil.entityToResponseDTO(avengers)).collect(Collectors.toList());
            return ResponseEntity.ok(new ResponseDTO(200, avengersResponseDTOS, "OK"));
        }
        throw new ShieldException(HttpStatus.BAD_REQUEST, "Error !!");
    }
}