package com.huijiewei.agile.serve.admin.config;

import com.huijiewei.agile.core.admin.repository.AdminAccessTokenRepository;
import com.huijiewei.agile.core.admin.repository.AdminRepository;
import com.huijiewei.agile.serve.admin.security.AdminAuthenticationUserDetailsService;
import com.huijiewei.agile.serve.admin.security.AdminPermissionEvaluator;
import com.huijiewei.agile.serve.admin.security.AdminPreAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@Configuration
@EnableWebSecurity
@Import(SecurityProblemSupport.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SecurityProblemSupport problemSupport;
    private final AdminPermissionEvaluator adminPermissionEvaluator;

    private final AdminRepository adminRepository;
    private final AdminAccessTokenRepository adminAccessTokenRepository;

    @Autowired
    public WebSecurityConfig(SecurityProblemSupport problemSupport, AdminPermissionEvaluator adminPermissionEvaluator, AdminRepository adminRepository, AdminAccessTokenRepository adminAccessTokenRepository) {
        this.problemSupport = problemSupport;
        this.adminPermissionEvaluator = adminPermissionEvaluator;
        this.adminRepository = adminRepository;
        this.adminAccessTokenRepository = adminAccessTokenRepository;
    }

    @Override
    public void configure(WebSecurity web) {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(this.adminPermissionEvaluator);

        web.expressionHandler(handler);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) {
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(new AdminAuthenticationUserDetailsService(this.adminRepository, this.adminAccessTokenRepository));

        builder.authenticationProvider(provider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/auth/login",
                        "/open/**",
                        "/files/**",
                        "/favicon.ico"
                )
                .permitAll()
                .anyRequest()
                .authenticated();

        http.cors();

        http.exceptionHandling().authenticationEntryPoint(problemSupport).accessDeniedHandler(problemSupport);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.csrf().disable();

        http.addFilter(new AdminPreAuthenticationFilter(this.authenticationManager()));
    }
}
