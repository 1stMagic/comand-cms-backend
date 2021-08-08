package com.biock.cms.shared.builder;

import com.biock.cms.shared.ContactData;

public class ContactDataBuilder implements Builder<ContactData> {

    private String company;
    private String street;
    private String postalCode;
    private String city;
    private String phone;
    private String mobile;
    private String fax;
    private String email;
    private String country;

    public ContactDataBuilder company(final String company) {

        this.company = company;
        return this;
    }

    public ContactDataBuilder street(final String street) {

        this.street = street;
        return this;
    }

    public ContactDataBuilder postalCode(final String postalCode) {

        this.postalCode = postalCode;
        return this;
    }

    public ContactDataBuilder city(final String city) {

        this.city = city;
        return this;
    }

    public ContactDataBuilder phone(final String phone) {

        this.phone = phone;
        return this;
    }

    public ContactDataBuilder mobile(final String mobile) {

        this.mobile = mobile;
        return this;
    }

    public ContactDataBuilder fax(final String fax) {

        this.fax = fax;
        return this;
    }

    public ContactDataBuilder email(final String email) {

        this.email = email;
        return this;
    }

    public ContactDataBuilder country(final String country) {

        this.country = country;
        return this;
    }
    
    @Override
    public ContactDataBuilder apply(final ContactData other) {

        if (other != null) {
            return company(other.getCompany())
                    .street(other.getStreet())
                    .postalCode(other.getPostalCode())
                    .city(other.getCity())
                    .phone(other.getPhone())
                    .mobile(other.getMobile())
                    .fax(other.getFax())
                    .email(other.getEmail())
                    .country(other.getCountry());
        }
        return this;
    }

    @Override
    public ContactData build() {

        return new ContactData(
                this.company,
                this.street,
                this.postalCode,
                this.city,
                this.phone,
                this.mobile,
                this.fax,
                this.email,
                this.country
        );
    }
}
