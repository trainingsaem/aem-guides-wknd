package com.adobe.aem.guides.wknd.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.Option;

@ObjectClassDefinition(
        name = "Email Notification - Configuration",
        description = "This configuration is used for setting up Email Notification service.")

public @interface EmailNotificationServiceConfig {

    @AttributeDefinition(
            name = "SMTP Host",
            description = "Enter SMTP server host name.",
            type = AttributeType.STRING)
    String smtpHost() default "smtp.example.com";

    @AttributeDefinition(
            name = "SMTP Port",
            description = "Enter SMTP port number.",
            type = AttributeType.INTEGER)
    int smtpPort() default 587;

    @AttributeDefinition(
            name = "SMTP Auth Enabled",
            description = "Enable SMTP authentication.",
            type = AttributeType.BOOLEAN)
    boolean smtpAuthEnabled() default true;

    @AttributeDefinition(
            name = "Default Recipients",
            description = "Comma-separated list of default recipient email addresses.",
            type = AttributeType.STRING)
    String[] defaultRecipients() default {"admin@wknd.com", "developer@wknd.com", "qa@wknd.com"};

    @AttributeDefinition(
            name = "Environment",
            description = "Environment in which this service is running.",
            options = {
                    @Option(label = "Development", value = "dev"),
                    @Option(label = "Staging", value = "stage"),
                    @Option(label = "Production", value = "prod")
            },
            type = AttributeType.STRING)
    String environment() default "dev";
}
