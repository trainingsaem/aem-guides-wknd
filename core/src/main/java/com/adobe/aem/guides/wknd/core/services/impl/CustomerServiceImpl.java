package com.adobe.aem.guides.wknd.core.services.impl;

import com.adobe.aem.guides.wknd.core.services.CustomerService;
import org.osgi.service.component.annotations.Component;

@Component(
        service = CustomerService.class,
        immediate = true
)
public class CustomerServiceImpl implements CustomerService {

    String customerName = "John Doe";

    @Override
    public String getCustomerName() {
        return customerName;
    }


    @Override
    public String buildCustomerName(String authoredName) {
        return (authoredName != null && !authoredName.isEmpty())
                ? authoredName.toUpperCase()
                : "John Doe";
    }

}
