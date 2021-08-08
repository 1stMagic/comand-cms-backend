package com.biock.cms.frontend.site.dto;

import com.biock.cms.shared.ContactData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ContactDataDTO {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String company;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String street;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("zip")
    private String postalCode;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String city;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("telephone")
    private String phone;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("mobilephone")
    private String mobile;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String fax;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String email;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String country;

    public static ContactDataDTO of(final ContactData entity) {

        if (entity == null) {
            return new ContactDataDTO();
        }
        return new ContactDataDTO()
                .setCompany(entity.getCompany())
                .setStreet(entity.getStreet())
                .setPostalCode(entity.getPostalCode())
                .setCity(entity.getCity())
                .setPhone(entity.getPhone())
                .setMobile(entity.getMobile())
                .setFax(entity.getFax())
                .setEmail(entity.getEmail())
                .setCountry(entity.getCountry());
    }

    public String getCompany() {

        return this.company;
    }

    public ContactDataDTO setCompany(final String company) {

        this.company = company;
        return this;
    }

    public String getStreet() {

        return this.street;
    }

    public ContactDataDTO setStreet(final String street) {

        this.street = street;
        return this;
    }

    public String getPostalCode() {

        return this.postalCode;
    }

    public ContactDataDTO setPostalCode(final String postalCode) {

        this.postalCode = postalCode;
        return this;
    }

    public String getCity() {

        return this.city;
    }

    public ContactDataDTO setCity(final String city) {

        this.city = city;
        return this;
    }

    public String getPhone() {

        return this.phone;
    }

    public ContactDataDTO setPhone(final String phone) {

        this.phone = phone;
        return this;
    }

    public String getMobile() {

        return this.mobile;
    }

    public ContactDataDTO setMobile(final String mobile) {

        this.mobile = mobile;
        return this;
    }

    public String getFax() {

        return this.fax;
    }

    public ContactDataDTO setFax(final String fax) {

        this.fax = fax;
        return this;
    }

    public String getEmail() {

        return this.email;
    }

    public ContactDataDTO setEmail(final String email) {

        this.email = email;
        return this;
    }

    public String getCountry() {

        return this.country;
    }

    public ContactDataDTO setCountry(final String country) {

        this.country = country;
        return this;
    }
}
