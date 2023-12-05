package com.perso.bio.service.notification_user;

import com.perso.bio.model.user_management.Validation;

public interface NotificationUserService {

    public void sendAccountConfirmationNotification(Validation validation);
}
