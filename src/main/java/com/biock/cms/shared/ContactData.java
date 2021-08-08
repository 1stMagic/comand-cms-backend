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

    public ContactData(
            final String company,
            final String street,
            final String postalCode,
            final String city,
            final String phone,
            final String mobile,
            final String fax,
            final String email,
            final String country) {

        this.company = company;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.phone = phone;
        this.mobile = mobile;
        this.fax = fax;
        this.email = email;
        this.country = country;
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
        return this.country.compareTo(other.country);
    }
}
