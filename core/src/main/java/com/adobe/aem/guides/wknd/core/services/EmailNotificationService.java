package com.adobe.aem.guides.wknd.core.services;

public interface EmailNotificationService {
    String getSmtpHost();

    int getSmtpPort();

    boolean isSmtpAuthEnabled();

    String[] getDefaultRecipients();

    String getEnvironment();
}
