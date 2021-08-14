package com.biock.cms.user;

import com.biock.cms.shared.AbstractEntity;
import com.biock.cms.shared.ContactData;
import com.biock.cms.user.builder.UserBuilder;

import java.util.HashSet;
import java.util.Set;

public class User extends AbstractEntity<User> {

    private final String email;
    private final String salutation;
    private final String title;
    private final String firstName;
    private final String lastName;
    private final ContactData contactData;
    private final boolean active;
    private final Set<UserGroup> groups;

    public User(
            final String id,
            final String email,
            final String salutation,
            final String title,
            final String firstName,
            final String lastName,
            final ContactData contactData,
            final boolean active,
            final Set<UserGroup> groups) {

        super(id);
        this.email = email;
        this.salutation = salutation;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactData = contactData;
        this.active = active;
        this.groups = new HashSet<>();
        if (groups != null) {
            this.groups.addAll(groups);
        }
    }

    public static UserBuilder builder() {

        return new UserBuilder();
    }

    public String getEmail() {

        return this.email;
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

    public ContactData getContactData() {

        return this.contactData;
    }

    public boolean isActive() {

        return this.active;
    }

    public Set<UserGroup> getGroups() {

        return new HashSet<>(this.groups);
    }

}
