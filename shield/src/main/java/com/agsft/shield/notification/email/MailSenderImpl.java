package com.agsft.shield.notification.email;

import com.agsft.shield.exception.ShieldException;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MailSenderImpl implements MailSender {

    @Value("${spring.mail.password}")
    private String apiKey;

    @Value("${spring.mail.from}")
    private String fromMail;

    @Override
    public String sendMail(String email, String subject, String content) {
        Email from = new Email(fromMail);
        Email to = new Email(email);
        Content body = new Content("text/plain", content);
        Mail mail = new Mail(from, subject, to, body);
        SendGrid sendGrid = new SendGrid(apiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getHeaders());
            return response.getBody();
        } catch (IOException exception) {
            throw new ShieldException(HttpStatus.EXPECTATION_FAILED, "emailSending",exception.getLocalizedMessage(),"failed to send mail");
        }
    }
}
