package com.biock.cms.user.builder;

import com.biock.cms.shared.ContactData;
import com.biock.cms.shared.builder.Builder;
import com.biock.cms.user.User;

public class UserBuilder implements Builder<User> {

    private String id;
    private String email;
    private String salutation;
    private String title;
    private String firstName;
    private String lastName;
    private ContactData contactData;
    private boolean active;
    private String[] groups;

    public UserBuilder id(final String id) {

        this.id = id;
        return this;
    }

    public UserBuilder email(final String email) {

        this.email = email;
        return this;
    }

    public UserBuilder salutation(final String salutation) {

        this.salutation = salutation;
        return this;
    }

    public UserBuilder title(final String title) {

        this.title = title;
        return this;
    }

    public UserBuilder firstName(final String firstName) {

        this.firstName = firstName;
        return this;
    }

    public UserBuilder lastName(final String lastName) {

        this.lastName = lastName;
        return this;
    }

    public UserBuilder contactData(final ContactData contactData) {

        this.contactData = contactData;
        return this;
    }

    public UserBuilder active(final boolean active) {

        this.active = active;
        return this;
    }

    public UserBuilder groups(final String[] groups) {

        this.groups = groups;
        return this;
    }

    @Override
    public UserBuilder apply(final User other) {

        if (other != null) {
            return id(other.getId())
                    .email(other.getEmail())
                    .salutation(other.getSalutation())
                    .title(other.getTitle())
                    .firstName(other.getFirstName())
                    .lastName(other.getLastName())
                    .contactData(other.getContactData())
                    .active(other.isActive())
                    .groups(other.getGroups());
        }
        return this;
    }

    @Override
    public User build() {

        return new User(
                this.id,
                this.email,
                this.salutation,
                this.title,
                this.firstName,
                this.lastName,
                this.contactData,
                this.active,
                this.groups);
    }
}
