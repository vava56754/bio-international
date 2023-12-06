package com.perso.bio.service.notification_user;

import com.perso.bio.model.procurement.Procurement;
import com.perso.bio.model.user_management.Validation;
import jakarta.mail.MessagingException;

public interface NotificationUserService {

    public void sendAccountConfirmationNotification(Validation validation) throws MessagingException;

    public void sendValidateProcurementNotification(Procurement procurement) throws MessagingException;

    public void sendValidateProcurementForAdminNotification(Procurement procurement) throws MessagingException;

    public void sendCompleteProcurementNotification(Procurement procurement) throws MessagingException;
}
