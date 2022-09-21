package com.ivanov.MyShop.security;

import com.ivanov.MyShop.models.Authority;
import com.ivanov.MyShop.models.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class PersonDetails implements UserDetails {

    private final Authority authority;

    public PersonDetails(Authority authority) {
        this.authority = authority;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(authority.getRole())); // Возвращает роли человека.
    }

    @Override
    public String getPassword() {
        return this.authority.getPassword();
    }

    @Override
    public String getUsername() {
        return authority.getName();
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

    // Нужно чтобы получать данные идентифицированного пользователя!
    public Authority getPerson() {
        return authority;
    }
}
