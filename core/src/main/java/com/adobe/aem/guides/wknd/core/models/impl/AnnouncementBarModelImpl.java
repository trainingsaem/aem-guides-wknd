package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.AnnouncementBarModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Slf4j
@Model(adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
        adapters = AnnouncementBarModel.class,
        resourceType = AnnouncementBarModelImpl.RESOURCE_TYPE
)
@Exporters(
        @Exporter(name = "jackson",
                selector = "announcementbardetails",
                extensions = "json",
                options = {
                        @ExporterOption(name = "SerializationFeature.WRITE_DATES_AS_TIMESTAMPS", value="true"),
                        @ExporterOption(name = "MapperFeature.SORT_PROPERTIES_ALPHABETICALLY", value = "true"),
                        @ExporterOption(name = "SerializationFeature.WRAP_ROOT_VALUE", value = "true")
                }
        )
)

@JsonRootName(value = "announcement-bar")
public class AnnouncementBarModelImpl implements AnnouncementBarModel{

    static final String RESOURCE_TYPE = "wknd/components/content/announcementbar";

    @JsonIgnore
    @JsonProperty(value = "announcement_text")
    @ValueMapValue
    @Getter
    private String announcementText;

    @Getter
    @ValueMapValue
    private String announcementCTAText;

    @ValueMapValue
    @Getter
    private String announcementCTALink;

    @JsonInclude
    @ValueMapValue
    private String jsonProperty = "JSON PROPERTY";

}
