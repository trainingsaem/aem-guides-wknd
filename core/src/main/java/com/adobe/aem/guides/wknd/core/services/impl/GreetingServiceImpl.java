package com.adobe.aem.guides.wknd.core.services.impl;

import com.adobe.aem.guides.wknd.core.services.GreetingService;
import org.osgi.service.component.annotations.Component;

@Component(
        service = GreetingService.class,
        immediate = true
)
public class GreetingServiceImpl implements GreetingService {

    String greetingMessage = "Greetings! How can we assist you today?";

    @Override
    public String getGreetingMessage() {
        return greetingMessage;
    }

    @Override
    public String buildGreetingMessage(String authoredMessage) {
        return (authoredMessage != null && !authoredMessage.isEmpty())
                ? authoredMessage.toUpperCase()
                : "Greetings! How can we assist you today?";
    }

}
