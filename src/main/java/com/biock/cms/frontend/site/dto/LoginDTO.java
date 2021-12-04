package com.biock.cms.frontend.site.dto;

import com.biock.cms.backend.site.dto.UserDTO;
import com.biock.cms.user.User;

public class LoginDTO {

    private String token;
    private UserDTO user;

    public static LoginDTO of(final String token, final User user) {

        return new LoginDTO()
                .setToken(token)
                .setUser(UserDTO.of(user));
    }

    public String getToken() {

        return this.token;
    }

    public LoginDTO setToken(final String token) {

        this.token = token;
        return this;
    }

    public UserDTO getUser() {

        return this.user;
    }

    public LoginDTO setUser(final UserDTO user) {

        this.user = user;
        return this;
    }
}
