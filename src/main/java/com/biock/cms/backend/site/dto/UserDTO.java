package com.biock.cms.backend.site.dto;

import com.biock.cms.frontend.site.dto.ContactDataDTO;
import com.biock.cms.user.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class UserDTO {

    private String email;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String salutation;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String title;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String firstName;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String lastName;
    private boolean active;
    private String[] groups;
    @JsonUnwrapped
    private ContactDataDTO contactData;

    public static UserDTO of(final User entity) {

        if (entity == null) {
            return new UserDTO();
        }
        return new UserDTO()
                .setEmail(entity.getEmail())
                .setSalutation(entity.getSalutation())
                .setTitle(entity.getTitle())
                .setFirstName(entity.getFirstName())
                .setLastName(entity.getLastName())
                .setActive(entity.isActive())
                .setGroups(entity.getGroups())
                .setContactData(ContactDataDTO.of(entity.getContactData()));
    }

    public String getEmail() {

        return this.email;
    }

    public UserDTO setEmail(final String email) {

        this.email = email;
        return this;
    }

    public String getSalutation() {

        return this.salutation;
    }

    public UserDTO setSalutation(final String salutation) {

        this.salutation = salutation;
        return this;
    }

    public String getTitle() {

        return this.title;
    }

    public UserDTO setTitle(final String title) {

        this.title = title;
        return this;
    }

    public String getFirstName() {

        return this.firstName;
    }

    public UserDTO setFirstName(final String firstName) {

        this.firstName = firstName;
        return this;
    }

    public String getLastName() {

        return this.lastName;
    }

    public UserDTO setLastName(final String lastName) {

        this.lastName = lastName;
        return this;
    }

    public boolean isActive() {

        return this.active;
    }

    public UserDTO setActive(final boolean active) {

        this.active = active;
        return this;
    }

    public String[] getGroups() {

        return this.groups;
    }

    public UserDTO setGroups(final String[] groups) {

        this.groups = groups;
        return this;
    }

    public ContactDataDTO getContactData() {

        return this.contactData;
    }

    public UserDTO setContactData(final ContactDataDTO contactData) {

        this.contactData = contactData;
        return this;
    }
}
