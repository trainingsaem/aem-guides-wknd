package com.adobe.aem.guides.wknd.core.services.impl;

import com.adobe.aem.guides.wknd.core.config.EmailNotificationServiceConfig;
import com.adobe.aem.guides.wknd.core.services.EmailNotificationService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
        service = EmailNotificationService.class,
        immediate = true)
@Designate(ocd = EmailNotificationServiceConfig.class)

public class EmailNotificationServiceImpl implements EmailNotificationService {

    static final Logger LOGGER = LoggerFactory.getLogger(EmailNotificationServiceImpl.class);

    private String smtpHost;
    private int smtpPort;
    private boolean smtpAuthEnabled;
    private String[] defaultRecipients;
    private String environment;

    @Activate
    @Modified
    protected void activate(EmailNotificationServiceConfig config) {
        this.smtpHost = config.smtpHost();
        this.smtpPort = config.smtpPort();
        this.smtpAuthEnabled = config.smtpAuthEnabled();
        this.defaultRecipients = config.defaultRecipients();
        this.environment = config.environment();

        LOGGER.info("\n Email Notification Config - SMTP Host: {}", smtpHost);
        LOGGER.info("\n Email Notification Config - SMTP Port: {}", smtpPort);
        LOGGER.info("\n Email Notification Config - SMTP Auth Enabled: {}", smtpAuthEnabled);
        LOGGER.info("\n Email Notification Config - Default Recipients: {}", String.join(", ", defaultRecipients));
        LOGGER.info("\n Email Notification Config - Environment: {}", environment);
    }

    @Override
    public String getSmtpHost() {
        return smtpHost;
    }

    @Override
    public int getSmtpPort() {
        return smtpPort;
    }

    @Override
    public boolean isSmtpAuthEnabled() {
        return smtpAuthEnabled;
    }

    @Override
    public String[] getDefaultRecipients() {
        return defaultRecipients;
    }

    @Override
    public String getEnvironment() {
        return environment;
    }
}
