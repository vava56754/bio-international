package com.perso.bio.service.notification_user;


import com.perso.bio.constants.Field;
import com.perso.bio.model.procurement.Procurement;
import com.perso.bio.model.user_management.Validation;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Qualifier("jpa")
public class NotificationUserServiceImpl implements NotificationUserService {

    JavaMailSender javaMailSender;

    @Autowired
    public NotificationUserServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendAccountConfirmationNotification(Validation validation) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(Field.ADMIN_MAIL);
        helper.setTo(validation.getUser().getUserMail());
        helper.setSubject("BIO INTERNATIONAL Confirmation de votre compte");
        String text = String.format("Bonjour %s, <br /> Votre code d'activation est %s, <br />Cordialement, <br />Bio International", validation.getUser().getUserName(), validation.getCode());
        helper.setText(text, true);

        javaMailSender.send(mimeMessage);
    }

    public void sendValidateProcurementNotification(Procurement procurement) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(Field.ADMIN_MAIL);
        helper.setTo(procurement.getUser().getUserMail());
        helper.setSubject("BIO INTERNATIONAL Confirmation de votre commande N°" + procurement.getProcurementId().toString());
        String text = String.format("Bonjour %s, <br /> Votre commande à bien été validée <br />" +
                "Vous recevrez un email de confirmation de votre commande par le propriétaire <br />" +
                "Cordialement, <br />Bio International", procurement.getUser().getUserName());
        helper.setText(text, true);

        javaMailSender.send(mimeMessage);
    }

    public void sendValidateProcurementForAdminNotification(Procurement procurement) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(Field.ADMIN_MAIL);
        helper.setTo(Field.ADMIN_MAIL);
        helper.setSubject("BIO INTERNATIONAL Nouvelle commande à valider");
        String text = String.format("Nouvelle Commande de %s à valider rendu dans votre espace admin!", procurement.getUser().getUserName());
        helper.setText(text, true);

        javaMailSender.send(mimeMessage);
    }

    public void sendCompleteProcurementNotification(Procurement procurement) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(Field.ADMIN_MAIL);
        helper.setTo(procurement.getUser().getUserMail());
        helper.setSubject("BIO INTERNATIONAL Confirmation de votre commande N°" + procurement.getProcurementId().toString());
        String text = String.format("Bonjour %s, <br /> Votre commande à bien été acceptée on vous contactera sous peu pour les détails ! <br />Cordialement, <br />Bio International", procurement.getUser().getUserName());
        helper.setText(text, true);

        javaMailSender.send(mimeMessage);
    }

}
