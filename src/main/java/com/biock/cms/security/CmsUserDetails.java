package com.biock.cms.security;

import com.biock.cms.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

public class CmsUserDetails implements UserDetails {

    private String userId;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public static CmsUserDetails of(final User user) {

        var userDetails = new CmsUserDetails();
        userDetails.userId = user.getId();
        userDetails.grantedAuthorities = Arrays.stream(user.getGroups()).map(SimpleGrantedAuthority::new).toList();
        return userDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return this.grantedAuthorities;
    }

    @Override
    public String getPassword() {

        return null;
    }

    @Override
    public String getUsername() {

        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }
}
