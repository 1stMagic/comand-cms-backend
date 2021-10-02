package com.biock.cms.backend.site.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UpdateUserDTO {

    private String email;
    private String salutation;
    private String title;
    private String firstName;
    private String lastName;
    private String company;
    private String street;
    private String postalCode;
    private String city;
    private String phone;
    private String mobile;
    private String fax;
    private String country;
    private String[] groups;
    private Boolean active;

    public String getEmail() {

        return this.email;
    }

    public UpdateUserDTO setEmail(final String email) {

        this.email = email;
        return this;
    }

    public String getSalutation() {

        return this.salutation;
    }

    public UpdateUserDTO setSalutation(final String salutation) {

        this.salutation = salutation;
        return this;
    }

    public String getTitle() {

        return this.title;
    }

    public UpdateUserDTO setTitle(final String title) {

        this.title = title;
        return this;
    }

    public String getFirstName() {

        return this.firstName;
    }

    public UpdateUserDTO setFirstName(final String firstName) {

        this.firstName = firstName;
        return this;
    }

    public String getLastName() {

        return this.lastName;
    }

    public UpdateUserDTO setLastName(final String lastName) {

        this.lastName = lastName;
        return this;
    }

    public String getCompany() {

        return this.company;
    }

    public UpdateUserDTO setCompany(final String company) {

        this.company = company;
        return this;
    }

    public String getStreet() {

        return this.street;
    }

    public UpdateUserDTO setStreet(final String street) {

        this.street = street;
        return this;
    }

    public String getPostalCode() {

        return this.postalCode;
    }

    public UpdateUserDTO setPostalCode(final String postalCode) {

        this.postalCode = postalCode;
        return this;
    }

    public String getCity() {

        return this.city;
    }

    public UpdateUserDTO setCity(final String city) {

        this.city = city;
        return this;
    }

    public String getPhone() {

        return this.phone;
    }

    public UpdateUserDTO setPhone(final String phone) {

        this.phone = phone;
        return this;
    }

    public String getMobile() {

        return this.mobile;
    }

    public UpdateUserDTO setMobile(final String mobile) {

        this.mobile = mobile;
        return this;
    }

    public String getFax() {

        return this.fax;
    }

    public UpdateUserDTO setFax(final String fax) {

        this.fax = fax;
        return this;
    }

    public String getCountry() {

        return this.country;
    }

    public UpdateUserDTO setCountry(final String country) {

        this.country = country;
        return this;
    }

    public String[] getGroups() {

        return this.groups;
    }

    public UpdateUserDTO setGroups(final String[] groups) {

        this.groups = groups;
        return this;
    }

    public Boolean isActive() {

        return this.active;
    }

    public UpdateUserDTO setActive(final Boolean active) {

        this.active = active;
        return this;
    }

    @JsonIgnore
    public boolean isEmpty() {

        return this.email == null
                && this.salutation == null
                && this.title == null
                && this.firstName == null
                && this.lastName == null
                && this.groups == null
                && this.active == null
                && !hasContactData();
    }

    @JsonIgnore
    public boolean hasContactData() {

        return this.company != null
                || this.street != null
                || this.postalCode != null
                || this.city != null
                || this.phone != null
                || this.mobile != null
                || this.fax != null
                || this.country != null;
    }
}
