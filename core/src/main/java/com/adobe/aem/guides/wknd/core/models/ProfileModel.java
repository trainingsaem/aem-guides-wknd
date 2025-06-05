package com.adobe.aem.guides.wknd.core.models;

import com.day.cq.wcm.api.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        adapters = ProfileModel.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
        resourceType = ProfileModel.RESOURCE_TYPE
)

@Exporters(
        @Exporter(name = "jackson",
                selector = "profiledetails",
                extensions = "json",
                options = {
                        @ExporterOption(name = "MapperFeature.SORT_PROPERTIES_ALPHABETICALLY", value = "true"),
                        @ExporterOption(name = "SerializationFeature.WRAP_ROOT_VALUE", value = "true")
                }
        )
)

@JsonRootName(value = "profile-details")
public class ProfileModel {

    static final String RESOURCE_TYPE = "wknd/components/content/profile/v2/profile";
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileModel.class);

    @ScriptVariable
    Page currentPage;
    private String currentPageTitle;

    @ScriptVariable
    ValueMap pageProperties;
    private String currentPageSubtitle;

    @SlingObject
    Resource currentResource;
    private String currentResourcePath;

    @SlingObject
    ResourceResolver resourceResolver;

    @SlingObject
    SlingHttpServletRequest request;
    private String currentUserId;

    @RequestAttribute
    private String id;

    @Self
    @Via("resource")
    Resource resource;

    @Inject
    @Path("/content/wknd/us/en/example-page")
    Resource examplePageResource;
    Page examplePage;
    private String examplePageTitle;
    private String examplePageNavigationTitle;

    @ResourcePath(name = "linkedPath")
    @Default(values = "/content/wknd")
    Page linkedPathPage;
    private String linkedPathPageTitle;
    private String linkedPathPageNavigationTitle;

    @ValueMapValue
    @Required
    @Default(values = "John Doe")
    private String name;

    @Inject
    @Source("valuemap")
    private String bio;

    @ValueMapValue
    private Integer age;

    @ValueMapValue
    private String dateOfBirth;

    @ValueMapValue
    private String profilePicture;

    @ValueMapValue
    private String[] profession;

    @ValueMapValue
    private boolean activeStatus;

    @Inject
    @Named("sling:resourceType")
    private String resourceType;

    @ChildResource
    private List<Resource> activitiesItems;

    @ChildResource
    private List<SocialLinksItemsModel> socialLinksItems;

    private String formattedDateOfBirth;

    @PostConstruct
    protected void init() {
        try {
            if (dateOfBirth != null && !dateOfBirth.isEmpty()) {
                try {
                    OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateOfBirth);
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy");
                    formattedDateOfBirth = offsetDateTime.format(dateTimeFormatter);
                } catch (DateTimeParseException e) {
                    formattedDateOfBirth = dateOfBirth;
                }
            } else {
                formattedDateOfBirth = "";
            }

            if (linkedPathPage != null) {
                linkedPathPageTitle = linkedPathPage.getTitle();
                linkedPathPageNavigationTitle = linkedPathPage.getNavigationTitle() != null ? linkedPathPage.getNavigationTitle() : "Page Path not found";
                LOGGER.info("\n Linked Path Page Title: {}", linkedPathPage.getTitle());
                LOGGER.info("\n Linked Path Page Navigation Title: {}", linkedPathPage.getNavigationTitle());
                LOGGER.info("\n Linked Path Page Page Title: {}", linkedPathPage.getPageTitle());
                LOGGER.info("\n Linked Path Page Description: {}", linkedPathPage.getDescription());
            } else {
                LOGGER.warn("Linked path page is null for path: /content/wknd");
            }

            if (examplePageResource != null) {
                examplePage = examplePageResource.adaptTo(Page.class);
                if (examplePage != null) {
                    examplePageTitle = examplePage.getTitle();
                    examplePageNavigationTitle = examplePage.getNavigationTitle();
                    LOGGER.info("\n Example Page Title: {}", examplePageTitle);
                    LOGGER.info("\n Example Page Page Title: {}", examplePage.getPageTitle());
                    LOGGER.info("\n Example Page Navigation Title:: {}", examplePage.getNavigationTitle());
                    LOGGER.info("\n Example Page Description: {}", examplePage.getDescription());
                } else {
                    LOGGER.warn("\n Failed to adapt examplePageResource to Page: {}", examplePageResource.getPath());
                }
            } else {
                LOGGER.warn("\n examplePageResource is null");
            }

            if (request != null && request.getUserPrincipal() != null) {
                currentUserId = request.getUserPrincipal().getName();
            }

            if (resource != null) {
                currentResourcePath = resource.getPath();
                LOGGER.info("\n Current Resource Path: {}", resource.getPath());
                LOGGER.info("\n Current Resource Name: {}", resource.getName());
                LOGGER.info("\n Current Resource Type: {}", resource.getResourceType());
                LOGGER.info("\n Current Resource Parent: {}", resource.getParent());
                LOGGER.info("\n Current Resource Children: {}", resource.listChildren());
            }

            if (pageProperties != null) {
                currentPageSubtitle = pageProperties.get("subtitle", String.class);
            }

            if (currentPage != null) {
                currentPageTitle = currentPage.getTitle();
                LOGGER.info("\n Current Page Title: {}", currentPage.getTitle());
                LOGGER.info("\n Current Page Page Title: {}", currentPage.getPageTitle());
                LOGGER.info("\n Current Page Navigation Title: {}", currentPage.getNavigationTitle());
                LOGGER.info("\n Current Page Description: {}", currentPage.getDescription());
                LOGGER.info("\n Current Page Path: {}", currentPage.getPath());
            }
        } catch (Exception e) {
            LOGGER.error("\n Exception during ProfileModel init(): ", e);
        }
    }


    public String getName() {
        return name.toUpperCase();
    }

    public String getBio() {
        return bio;
    }

    public Integer getAge() {
        return age;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String[] getProfession() {
        return profession;
    }

    public boolean isActiveStatus() {
        return activeStatus;
    }

    public String getResourceType() {
        return resourceType;
    }

    public String getFormattedDateOfBirth() {
        return formattedDateOfBirth;
    }

    public List<Resource> getActivitiesItems() {
        return activitiesItems;
    }

    public List<SocialLinksItemsModel> getSocialLinksItems() {
        return socialLinksItems;
    }

    @JsonIgnore
    public String getCurrentPageTitle() {
        return currentPageTitle;
    }

    @JsonIgnore
    public String getCurrentPageSubtitle() {
        return currentPageSubtitle;
    }

    @JsonIgnore
    public String getCurrentResourcePath() {
        return currentResourcePath;
    }

    @JsonIgnore
    public String getCurrentUserId() {
        return currentUserId;
    }

    public String getLinkedPathPageTitle() {
        return linkedPathPageTitle;
    }

    public String getExamplePageTitle() {
        return examplePageTitle;
    }

    public String getExamplePageNavigationTitle() {
        return examplePageNavigationTitle;
    }

    public String getLinkedPathPageNavigationTitle() {
        return linkedPathPageNavigationTitle;
    }

    @JsonIgnore
    public String getPath() {
        return resource.getPath();
    }
    @JsonIgnore
    public String getId() {
        return id;
    }
}
