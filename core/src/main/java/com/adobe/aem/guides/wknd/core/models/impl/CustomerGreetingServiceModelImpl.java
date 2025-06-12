package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.CustomerGreetingServiceModel;
import com.adobe.aem.guides.wknd.core.services.CustomerGreetingService;
import com.adobe.aem.guides.wknd.core.services.CustomerService;
import com.adobe.aem.guides.wknd.core.services.GreetingService;
import com.adobe.aem.guides.wknd.core.services.WKNDCustomService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
        adapters = CustomerGreetingServiceModel.class
)
public class CustomerGreetingServiceModelImpl implements CustomerGreetingServiceModel {

    static final Logger LOGGER = LoggerFactory.getLogger(CustomerGreetingServiceModelImpl.class);

    @OSGiService
    GreetingService greetingService;

    @OSGiService
    CustomerService customerService;

    @OSGiService
    CustomerGreetingService customerGreetingService;

    @OSGiService
    WKNDCustomService wkndCustomService;

    @ValueMapValue
    private String greetingMessageFromDialog;

    @ValueMapValue
    private String customerNameFromDialog;

    private String greetingMessage;
    private String customerName;
    private String customerGreetingMessage;
    private String greetingMessageBuiltFromDialog;
    private String customerNameBuiltFromDialog;
    private String apiEndpoint;
    private String jsonDataReceivedFromEndpoint;

    @PostConstruct
    protected void init() {
        greetingMessage = greetingService.getGreetingMessage();
        LOGGER.info("\n Greeting Message from GreetingService: {} ", greetingMessage);

        customerName = customerService.getCustomerName();
        LOGGER.info("\n Customer Name from CustomerService: {} ", customerName);

        customerGreetingMessage = customerGreetingService.getCustomerGreetingMessage();
        LOGGER.info("\n Customer Greeting Message from CustomerGreetingService: {} ", customerGreetingMessage);

        greetingMessageBuiltFromDialog = greetingService.buildGreetingMessage(greetingMessageFromDialog);
        LOGGER.info("\n Greeting Message from Dialog: {} ", greetingMessageFromDialog);
        LOGGER.info("\n Greeting Message built from Dialog: {} ", greetingMessageBuiltFromDialog);

        customerNameBuiltFromDialog = customerService.buildCustomerName(customerNameFromDialog);
        LOGGER.info("\n Customer Name from Dialog: {} ", customerNameFromDialog);
        LOGGER.info("\n Customer Name built from Dialog: {} ", customerNameBuiltFromDialog);

        apiEndpoint = wkndCustomService.apiEndpoint();
        LOGGER.info("API Endpoint URL from WKNDCustomService: {}", apiEndpoint);

        jsonDataReceivedFromEndpoint = wkndCustomService.performAction();
        LOGGER.info("JSON Data received from Endpoint form Model: {}", jsonDataReceivedFromEndpoint);


    }

    @Override
    public String getCustomerGreetingMessage() {
        return customerGreetingMessage;
    }

    @Override
    public String getGreetingMessage() {
        return greetingMessage;
    }

    @Override
    public String getCustomerName() {
        return customerName;
    }

    @Override
    public String getGreetingMessageBuiltFromDialog() {
        return greetingMessageBuiltFromDialog;
    }

    @Override
    public String getCustomerNameBuiltFromDialog() {
        return customerNameBuiltFromDialog;
    }

    @Override
    public String getApiEndpoint() {
        return apiEndpoint;
    }

    @Override
    public String getJsonDataReceivedFromEndpoint() {
        return jsonDataReceivedFromEndpoint;
    }
}
