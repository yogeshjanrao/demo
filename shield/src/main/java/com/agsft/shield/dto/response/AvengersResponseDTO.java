package com.agsft.shield.dto.response;

import com.agsft.shield.dto.custom.AvengerMissionAssignmentCustom;
import com.agsft.shield.status.AvengerStatus;
import com.agsft.shield.status.NotificationService;

import java.util.Set;

public class AvengersResponseDTO {
    private Long id;
    private String name;
    private AvengerStatus status;
    private NotificationService notificationService;
    private Set<AvengerMissionAssignmentCustom> avengerMissionAssignments;
    private String contactNo;
    private String mailId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AvengerStatus getStatus() {
        return status;
    }

    public void setStatus(AvengerStatus status) {
        this.status = status;
    }

    public NotificationService getNotificationService() {
        return notificationService;
    }

    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public Set<AvengerMissionAssignmentCustom> getAvengerMissionAssignments() {
        return avengerMissionAssignments;
    }

    public void setAvengerMissionAssignments(Set<AvengerMissionAssignmentCustom> avengerMissionAssignments) {
        this.avengerMissionAssignments = avengerMissionAssignments;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    @Override
    public String toString() {
        return "AvengersResponseDTO{" + "name='" + name + '\'' + ", status=" + status + ", notificationService=" + notificationService + ", contactNo='" + contactNo + '\'' + ", mailId='" + mailId + '\'' + ", avengerMissionAssignments=" + avengerMissionAssignments + '}';
    }
}
