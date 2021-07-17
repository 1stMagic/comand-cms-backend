package com.biock.cms.site;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;

public class SiteContactData {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String salutation;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String title;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String firstName;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String lastName;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String street;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String postalCode;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String city;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String phone;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String fax;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String email;

    public SiteContactData(
            @NotNull final String salutation,
            @NotNull final String title,
            @NotNull final String firstName,
            @NotNull final String lastName,
            @NotNull final String street,
            @NotNull final String postalCode,
            @NotNull final String city,
            @NotNull final String phone,
            @NotNull final String fax,
            @NotNull final String email) {

        this.salutation = salutation;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.phone = phone;
        this.fax = fax;
        this.email = email;
    }

    public static Builder builder() {

        return new Builder();
    }

    public static SiteContactData empty() {

        return builder().build();
    }

    public String getSalutation() {

        return this.salutation;
    }

    public String getTitle() {

        return this.title;
    }

    public String getFirstName() {

        return this.firstName;
    }

    public String getLastName() {

        return this.lastName;
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

    public String getFax() {

        return this.fax;
    }

    public String getEmail() {

        return this.email;
    }

    public static final class Builder implements com.biock.cms.shared.Builder<SiteContactData> {

        private String salutation;
        private String title;
        private String firstName;
        private String lastName;
        private String street;
        private String postalCode;
        private String city;
        private String phone;
        private String fax;
        private String email;

        public Builder salutation(final String salutation) {

            this.salutation = salutation;
            return this;
        }

        public Builder title(final String title) {

            this.title = title;
            return this;
        }

        public Builder firstName(final String firstName) {

            this.firstName = firstName;
            return this;
        }

        public Builder lastName(final String lastName) {

            this.lastName = lastName;
            return this;
        }

        public Builder street(final String street) {

            this.street = street;
            return this;
        }

        public Builder postalCode(final String postalCode) {

            this.postalCode = postalCode;
            return this;
        }

        public Builder city(final String city) {

            this.city = city;
            return this;
        }

        public Builder phone(final String phone) {

            this.phone = phone;
            return this;
        }

        public Builder fax(final String fax) {

            this.fax = fax;
            return this;
        }

        public Builder email(final String email) {

            this.email = email;
            return this;
        }

        @Override
        public SiteContactData build() {

            return new SiteContactData(
                    StringUtils.defaultString(this.salutation),
                    StringUtils.defaultString(this.title),
                    StringUtils.defaultString(this.firstName),
                    StringUtils.defaultString(this.lastName),
                    StringUtils.defaultString(this.street),
                    StringUtils.defaultString(this.postalCode),
                    StringUtils.defaultString(this.city),
                    StringUtils.defaultString(this.phone),
                    StringUtils.defaultString(this.fax),
                    StringUtils.defaultString(this.email)
            );
        }
    }
}
