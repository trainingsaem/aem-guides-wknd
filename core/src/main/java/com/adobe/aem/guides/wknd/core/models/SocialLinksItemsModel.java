package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;


import javax.inject.Inject;

@Model(adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class SocialLinksItemsModel {

    @Inject
    private String platform;

    @Inject
    private String url;

    public String getPlatform() {
        return platform;
    }

    public String getUrl() {
        return url;
    }
}
