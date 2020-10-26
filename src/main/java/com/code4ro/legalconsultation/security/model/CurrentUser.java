package com.code4ro.legalconsultation.security.model;

import com.code4ro.legalconsultation.authentication.model.persistence.ApplicationUser;
import com.code4ro.legalconsultation.user.model.persistence.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

@Getter
public class CurrentUser implements UserDetails {
    private final UUID id;

    private final String fullName;

    private final String username;

    private final UserRole role;

    @JsonIgnore
    private final String email;

    @JsonIgnore
    private final String password;


    public CurrentUser(UUID id, String fullName, String username, String email, String password, UserRole role) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public static CurrentUser create(ApplicationUser applicationUser) {
        return new CurrentUser(
                applicationUser.getId(),
                getFullName(applicationUser),
                applicationUser.getUsername(),
                applicationUser.getUser().getEmail(),
                applicationUser.getPassword(),
                applicationUser.getUser().getRole()
        );
    }

    private static String getFullName(final ApplicationUser applicationUser) {
        return String
                .format("%s %s", applicationUser.getUser().getFirstName(), applicationUser.getUser().getLastName());
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrentUser that = (CurrentUser) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
