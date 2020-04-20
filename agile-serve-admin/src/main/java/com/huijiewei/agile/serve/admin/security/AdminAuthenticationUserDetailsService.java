package com.huijiewei.agile.serve.admin.security;

import com.huijiewei.agile.core.admin.entity.Admin;
import com.huijiewei.agile.core.admin.entity.AdminAccessToken;
import com.huijiewei.agile.core.admin.repository.AdminAccessTokenRepository;
import com.huijiewei.agile.core.admin.repository.AdminRepository;
import com.huijiewei.agile.core.admin.security.AdminIdentity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Optional;

public class AdminAuthenticationUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
    private final AdminRepository adminRepository;
    private final AdminAccessTokenRepository adminAccessTokenRepository;

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

        if (adminAccessTokenOptional.isEmpty()) {
            throw new BadCredentialsException("无效的 AccessToken");
        }

        AdminAccessToken adminAccessToken = adminAccessTokenOptional.get();

        Optional<Admin> adminOptional = this.adminRepository.findById(adminAccessToken.getAdminId());

        if (adminOptional.isEmpty()) {
            throw new BadCredentialsException("无效的 AccessToken");
        }

        AdminIdentity identity = new AdminIdentity();
        identity.setAdmin(adminOptional.get());
        identity.setClientId(clientId);

        return new AdminUserDetails(identity);
    }
}
