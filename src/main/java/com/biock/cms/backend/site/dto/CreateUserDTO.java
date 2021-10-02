package com.biock.cms.backend.site.dto;

import javax.validation.constraints.NotBlank;

public class CreateUserDTO {

    @NotBlank
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

    public String getEmail() {

        return this.email;
    }

    public CreateUserDTO setEmail(final String email) {

        this.email = email;
        return this;
    }

    public String getSalutation() {

        return this.salutation;
    }

    public CreateUserDTO setSalutation(final String salutation) {

        this.salutation = salutation;
        return this;
    }

    public String getTitle() {

        return this.title;
    }

    public CreateUserDTO setTitle(final String title) {

        this.title = title;
        return this;
    }

    public String getFirstName() {

        return this.firstName;
    }

    public CreateUserDTO setFirstName(final String firstName) {

        this.firstName = firstName;
        return this;
    }

    public String getLastName() {

        return this.lastName;
    }

    public CreateUserDTO setLastName(final String lastName) {

        this.lastName = lastName;
        return this;
    }

    public String getCompany() {

        return this.company;
    }

    public CreateUserDTO setCompany(final String company) {

        this.company = company;
        return this;
    }

    public String getStreet() {

        return this.street;
    }

    public CreateUserDTO setStreet(final String street) {

        this.street = street;
        return this;
    }

    public String getPostalCode() {

        return this.postalCode;
    }

    public CreateUserDTO setPostalCode(final String postalCode) {

        this.postalCode = postalCode;
        return this;
    }

    public String getCity() {

        return this.city;
    }

    public CreateUserDTO setCity(final String city) {

        this.city = city;
        return this;
    }

    public String getPhone() {

        return this.phone;
    }

    public CreateUserDTO setPhone(final String phone) {

        this.phone = phone;
        return this;
    }

    public String getMobile() {

        return this.mobile;
    }

    public CreateUserDTO setMobile(final String mobile) {

        this.mobile = mobile;
        return this;
    }

    public String getFax() {

        return this.fax;
    }

    public CreateUserDTO setFax(final String fax) {

        this.fax = fax;
        return this;
    }

    public String getCountry() {

        return this.country;
    }

    public CreateUserDTO setCountry(final String country) {

        this.country = country;
        return this;
    }

    public String[] getGroups() {

        return this.groups;
    }

    public CreateUserDTO setGroups(final String[] groups) {

        this.groups = groups;
        return this;
    }
}
