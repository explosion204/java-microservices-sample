package com.epam.microserviceslearning.storageservice.security;

import com.epam.microserviceslearning.storageservice.security.model.User;
import com.epam.microserviceslearning.storageservice.security.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class GithubAuthentication implements Authentication {
    private final String token;

    private List<UserRole> roles;

    private User user;
    private boolean isAuthenticated;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public void setPrincipal(User user) {
        this.user = user;
    }

    public void setAuthorities(List<UserRole> authorities) {
        this.roles = authorities;
    }

    @Override
    public String getName() {
        return user != null
                ? String.valueOf(user.getId())
                : StringUtils.EMPTY;
    }
}
