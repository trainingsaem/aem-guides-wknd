package com.adobe.aem.guides.wknd.core.models;

public interface CustomerGreetingServiceModel {
    String getGreetingMessage();

    String getCustomerName();

    String getCustomerGreetingMessage();

    String getGreetingMessageBuiltFromDialog();

    String getCustomerNameBuiltFromDialog();

    String getApiEndpoint();

    String getJsonDataReceivedFromEndpoint();
}
