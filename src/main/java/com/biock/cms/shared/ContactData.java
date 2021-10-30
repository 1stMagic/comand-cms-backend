package com.biock.cms.shared;

import com.biock.cms.shared.builder.ContactDataBuilder;

public class ContactData implements ValueObject<ContactData> {

    private final String company;
    private final String street;
    private final String postalCode;
    private final String city;
    private final String phone;
    private final String mobile;
    private final String fax;
    private final String email;
    private final String country;
    private final String postOfficeBox;
    private final String website;
    private final String otherInformation;

    public ContactData(
            final String company,
            final String street,
            final String postalCode,
            final String city,
            final String phone,
            final String mobile,
            final String fax,
            final String email,
            final String country,
            final String postOfficeBox,
            final String website,
            final String otherInformation) {

        this.company = company;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.phone = phone;
        this.mobile = mobile;
        this.fax = fax;
        this.email = email;
        this.country = country;
        this.postOfficeBox = postOfficeBox;
        this.website = website;
        this.otherInformation = otherInformation;
    }

    public static ContactDataBuilder builder() {

        return new ContactDataBuilder();
    }

    public static ContactData empty() {

        return builder().build();
    }

    public String getCompany() {

        return this.company;
    }

    public String getStreet() {

        return this.street;
    }

    public String getPostalCode() {

        return this.postalCode;
    }

    public String getCity() {

        return this.city;
    }

    public String getPhone() {

        return this.phone;
    }

    public String getMobile() {

        return this.mobile;
    }

    public String getFax() {

        return this.fax;
    }

    public String getEmail() {

        return this.email;
    }

    public String getCountry() {

        return this.country;
    }

    public String getPostOfficeBox() {

        return this.postOfficeBox;
    }

    public String getWebsite() {

        return this.website;
    }

    public String getOtherInformation() {

        return this.otherInformation;
    }

    @Override
    public int compareTo(final ContactData other) {

        int c = this.company.compareTo(other.company);
        if (c != 0) {
            return c;
        }
        c = this.street.compareTo(other.street);
        if (c != 0) {
            return c;
        }
        c = this.postalCode.compareTo(other.postalCode);
        if (c != 0) {
            return c;
        }
        c = this.city.compareTo(other.city);
        if (c != 0) {
            return c;
        }
        c = this.phone.compareTo(other.phone);
        if (c != 0) {
            return c;
        }
        c = this.mobile.compareTo(other.mobile);
        if (c != 0) {
            return c;
        }
        c = this.fax.compareTo(other.fax);
        if (c != 0) {
            return c;
        }
        c = this.email.compareTo(other.email);
        if (c != 0) {
            return c;
        }
        c = this.country.compareTo(other.country);
        if (c != 0) {
            return c;
        }
        c = this.postOfficeBox.compareTo(other.postOfficeBox);
        if (c != 0) {
            return c;
        }
        c = this.website.compareTo(other.website);
        if (c != 0) {
            return c;
        }
        return this.otherInformation.compareTo(other.otherInformation);
    }
}
