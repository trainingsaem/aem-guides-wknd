package com.adobe.aem.guides.wknd.core.services;

public interface GreetingService {
    String getGreetingMessage();

    String buildGreetingMessage(String authoredMessage);
}
