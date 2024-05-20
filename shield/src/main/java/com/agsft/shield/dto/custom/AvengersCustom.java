package com.agsft.shield.dto.custom;

import com.agsft.shield.status.AvengerStatus;
import com.agsft.shield.status.NotificationService;

public class AvengersCustom {
    private String name;
    private AvengerStatus status;
    private NotificationService notificationService;

    private String contactNo;
    private String mailId;

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

    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public String toString() {
        return "AvengersCustom{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", notificationService=" + notificationService +
                ", contactNo='" + contactNo + '\'' +
                ", mailId='" + mailId + '\'' +
                '}';
    }
}
