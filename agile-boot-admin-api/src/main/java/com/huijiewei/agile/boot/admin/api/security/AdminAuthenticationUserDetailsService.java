package com.huijiewei.agile.boot.admin.api.security;

import com.huijiewei.agile.base.admin.entity.Admin;
import com.huijiewei.agile.base.admin.entity.AdminAccessToken;
import com.huijiewei.agile.base.admin.repository.AdminAccessTokenRepository;
import com.huijiewei.agile.base.admin.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Optional;

public class AdminAuthenticationUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
    private AdminRepository adminRepository;
    private AdminAccessTokenRepository adminAccessTokenRepository;

    public AdminAuthenticationUserDetailsService(AdminRepository adminRepository, AdminAccessTokenRepository adminAccessTokenRepository) {
        this.adminRepository = adminRepository;
        this.adminAccessTokenRepository = adminAccessTokenRepository;
    }

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
        String clientId = (String) token.getPrincipal();
        String accessToken = (String) token.getCredentials();

        if (accessToken.isEmpty()) {
            return null;
        }

        AdminAccessToken adminAccessToken = this.adminAccessTokenRepository.findByClientIdAndAccessToken(clientId, accessToken);

        if (adminAccessToken == null) {
            return null;
        }

        Optional<Admin> admin = this.adminRepository.findById(adminAccessToken.getAdminId());

        if (admin.isEmpty()) {
            return null;
        }

        return new AdminUserDetails(admin.get());
    }
}
