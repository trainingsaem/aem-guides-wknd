package com.adobe.aem.guides.wknd.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(
        name = "WKND Custom Service Configuration",
        description = "WKND Custom Service Configuration for custom OSGI Component for getting JSON API Endpoint"
)

public @interface WKNDCustomServiceConfig {

    @AttributeDefinition(
            name = "API Endpoint",
            description = "The API Endpoint for WKNDCustomService for getting JSON API Endpoint"
    )
    String api_endpoint() default "https://default.example.com";

}
