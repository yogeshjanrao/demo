package com.agsft.shield.notification.sms;

import com.agsft.shield.exception.ShieldException;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class SmsSenderImpl implements SmsSender {

    @Value("${twilio.authSid}")
    private String authSid;
    @Value("${twilio.secret}")
    private String password;
    @Value("${twilio.massage.sid}")
    private String massageSid;

    @Override
    public boolean sentSms(String phoneNo, String massage) {
        Twilio.init(authSid, password);
        Message message = Message.creator(new PhoneNumber(phoneNo), massageSid, massage).create();
        if (!message.getSid().isEmpty() && !message.getSid().isBlank()) {
            return true;
        }
        throw new ShieldException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed To Send Massage");
    }
}
