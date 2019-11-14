package com.huijiewei.agile.boot.admin.api.security;

import com.huijiewei.agile.base.admin.entity.Admin;
import com.huijiewei.agile.base.admin.entity.AdminAccessToken;
import com.huijiewei.agile.base.admin.repository.AdminAccessTokenRepository;
import com.huijiewei.agile.base.admin.repository.AdminRepository;
import com.huijiewei.agile.base.admin.security.AdminUser;
import com.huijiewei.agile.base.admin.security.AdminUserDetails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Optional;

public class AdminAuthenticationUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
    private AdminRepository adminRepository;
    private AdminAccessTokenRepository adminAccessTokenRepository;

    public AdminAuthenticationUserDetailsService(AdminRepository adminRepository, AdminAccessTokenRepository adminAccessTokenRepository) {
        this.adminRepository = adminRepository;
        this.adminAccessTokenRepository = adminAccessTokenRepository;
    }

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws AuthenticationException {
        String clientId = (String) token.getPrincipal();
        String accessToken = (String) token.getCredentials();

        if (StringUtils.isEmpty(accessToken)) {
            throw new BadCredentialsException("无效的 AccessToken");
        }

        Optional<AdminAccessToken> adminAccessTokenOptional = this.adminAccessTokenRepository.findByClientIdAndAccessToken(clientId, accessToken);

        if (!adminAccessTokenOptional.isPresent()) {
            throw new BadCredentialsException("无效的 AccessToken");
        }

        AdminAccessToken adminAccessToken = adminAccessTokenOptional.get();

        Optional<Admin> adminOptional = this.adminRepository.findById(adminAccessToken.getAdminId());

        if (!adminOptional.isPresent()) {
            throw new BadCredentialsException("无效的 AccessToken");
        }

        AdminUser adminUser = new AdminUser();
        adminUser.setAdmin(adminOptional.get());
        adminUser.setClientId(clientId);

        return new AdminUserDetails(adminUser);
    }
}
