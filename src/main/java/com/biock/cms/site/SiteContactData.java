package com.biock.cms.site;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

public class SiteContactData {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String company;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String street;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("zip")
    private final String postalCode;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String city;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("telephone")
    private final String phone;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("mobilephone")
    private final String mobile;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String fax;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String email;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String country;

    public SiteContactData(
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

    public static Builder builder() {

        return new Builder();
    }

    public static SiteContactData empty() {

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

    public static final class Builder implements com.biock.cms.shared.Builder<SiteContactData> {

        private String company;
        private String street;
        private String postalCode;
        private String city;
        private String phone;
        private String mobile;
        private String fax;
        private String email;
        private String country;

        public Builder company(final String company) {

            this.company = company;
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

        public Builder mobile(final String mobile) {

            this.mobile = mobile;
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

        public Builder country(final String country) {

            this.country = country;
            return this;
        }

        @Override
        public SiteContactData build() {

            return new SiteContactData(
                    StringUtils.defaultString(this.company),
                    StringUtils.defaultString(this.street),
                    StringUtils.defaultString(this.postalCode),
                    StringUtils.defaultString(this.city),
                    StringUtils.defaultString(this.phone),
                    StringUtils.defaultString(this.mobile),
                    StringUtils.defaultString(this.fax),
                    StringUtils.defaultString(this.email),
                    StringUtils.defaultString(this.country)
            );
        }
    }
}
