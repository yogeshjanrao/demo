package com.agsft.shield.entitiy;

import com.agsft.shield.status.AvengerStatus;
import com.agsft.shield.status.NotificationService;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "avengers")
public class Avengers extends Auditing {
    @Column(name = "name", nullable = false)
    private String name;
    @Transient
    private AvengerStatus status;
    @Column(name = "notification_service")
    @Enumerated(EnumType.STRING)
    private NotificationService notificationService;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "avengers")
    private Set<AvengerMissionAssignment> avengerMissionAssignments;
    @Column(name = "contact_no")
    private String contactNo;
    @Column(name = "mail_id")
    private String mailId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

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

    public Set<AvengerMissionAssignment> getAvengerMissionAssignments() {
        return avengerMissionAssignments;
    }

    public void setAvengerMissionAssignments(Set<AvengerMissionAssignment> avengerMissionAssignments) {
        this.avengerMissionAssignments = avengerMissionAssignments;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getMailId() {
        return mailId;    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
