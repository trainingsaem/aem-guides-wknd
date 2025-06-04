package com.adobe.aem.guides.wknd.core.models;

import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ResourcePath;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)

public class ProfileModel {

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

    @ResourcePath(path = "/content/wknd/us/en/example-page")
    Page examplePage;
    private String examplePageTitle;
    private String examplePageNavigationTitle;

    @Inject
    @Required
    @Default(values = "John Doe")
    private String name;

    @Inject
    private String bio;

    @Inject
    private Integer age;

    @Inject
    private String dateOfBirth;

    @Inject
    private String profilePicture;

    @Inject
    private String[] profession;

    @Inject
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

        if (examplePage != null) {
           examplePageTitle = examplePage.getTitle();
        }
        examplePageNavigationTitle = examplePage != null ? examplePage.getNavigationTitle() : "Page Path not found";

        currentUserId = Objects.requireNonNull(request.getUserPrincipal()).getName();

        currentResourcePath = currentResource.getPath();
        LOGGER.info("Current Resource Path: {}", currentResource.getPath());
        LOGGER.info("Current Resource Name: {}", currentResource.getName());
        LOGGER.info("Current Resource Type: {}", currentResource.getResourceType());
        LOGGER.info("Current Resource Parent: {}", currentResource.getParent());
        LOGGER.info("Current Resource Children: {}", currentResource.listChildren());

        currentPageSubtitle = pageProperties.get("subtitle", String.class);
        currentPageTitle = currentPage.getTitle();
        LOGGER.info("Current Page Title: {}", currentPage.getTitle());
        LOGGER.info("Current Page Title: {}", currentPage.getPageTitle());
        LOGGER.info("Current Page Navigation Title: {}", currentPage.getNavigationTitle());
        LOGGER.info("Current Page Description: {}", currentPage.getDescription());
        LOGGER.info("Current Page Path: {}", currentPage.getPath());
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

    public String getCurrentPageTitle() {
        return currentPageTitle;
    }

    public String getCurrentPageSubtitle() {
        return currentPageSubtitle;
    }

    public String getCurrentResourcePath() {
        return currentResourcePath;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public String getExamplePageTitle() {
        return examplePageTitle;
    }

    public String getExamplePageNavigationTitle() {
        return examplePageNavigationTitle;
    }
}
