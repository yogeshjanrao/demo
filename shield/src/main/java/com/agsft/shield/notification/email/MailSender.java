package com.agsft.shield.notification.email;

public interface MailSender {

    String sendMail(String email, String subject, String content);

}
