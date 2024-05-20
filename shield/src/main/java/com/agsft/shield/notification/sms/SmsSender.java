package com.agsft.shield.notification.sms;

public interface SmsSender {
    boolean sentSms(String phoneNo, String massage);
}
