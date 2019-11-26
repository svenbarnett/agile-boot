package com.huijiewei.agile.boot.admin.api.security;

import com.huijiewei.agile.base.admin.security.AdminIdentity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AdminUserDetails implements UserDetails {
    private AdminIdentity identity;

    AdminUserDetails(AdminIdentity identity) {
        this.identity = identity;
    }

    public static AdminIdentity getCurrentAdminIdentity() {
        AdminUserDetails adminUserDetails = (AdminUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return adminUserDetails.getAdminIdentity();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.identity.getAdmin().getName();
    }

    AdminIdentity getAdminIdentity() {
        return this.identity;
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
