package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Required;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)

public class ProfileModel {

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

}
