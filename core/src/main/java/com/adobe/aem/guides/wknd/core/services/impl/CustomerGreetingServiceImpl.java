package com.adobe.aem.guides.wknd.core.services.impl;

import com.adobe.aem.guides.wknd.core.services.CustomerGreetingService;
import com.adobe.aem.guides.wknd.core.services.CustomerService;
import com.adobe.aem.guides.wknd.core.services.GreetingService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        service = CustomerGreetingService.class,
        immediate = true
)

public class CustomerGreetingServiceImpl implements CustomerGreetingService {

    @Reference
    GreetingService greetingService;

    @Reference
    CustomerService customerService;

    @Override
    public String getCustomerGreetingMessage() {
        String customerName = customerService.getCustomerName();
        String greetingMessage = greetingService.getGreetingMessage();
        return "Hello " + customerName + "!!\n" + greetingMessage;
    }
}
