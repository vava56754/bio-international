package com.perso.bio.service.notification_user;



import com.perso.bio.model.user_management.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Qualifier("jpa")
public class NotificationUserServiceImpl implements NotificationUserService {

    JavaMailSender javaMailSender;

    @Autowired
    public NotificationUserServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendAccountConfirmationNotification(Validation validation) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("no-reply@bio-international.com");
        mailMessage.setTo(validation.getUser().getUserMail());
        mailMessage.setSubject("BIO INTERNATIONAL Confirmation de votre compte");
        String text = String.format("Bonjour %s, <br /> Votre code d'activation est %s, <br />Cordialement, <br />Bio International", validation.getUser().getUserName(), validation.getCode());
        mailMessage.setText(text);

        javaMailSender.send(mailMessage);
    }

}
