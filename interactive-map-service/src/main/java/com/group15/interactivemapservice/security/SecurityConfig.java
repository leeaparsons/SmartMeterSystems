package com.group15.interactivemapservice.security;

import com.fortress.security.entity.Role;
import com.fortress.security.security.FortressConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends FortressConfiguration {

    private final  String[] authEndpoints = {
            "/tabledata",
            "/search"
    };

    private final String[] publicPoints = {
            "/mapView/**",
            "/tableView/**",
            "/dashboardView/**",
            "/test"
    };

    @Override
    public void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authz,
                          HttpSecurity http) {
        authz.requestMatchers(publicPoints)
                .permitAll()
                .requestMatchers(authEndpoints)
                .hasAnyAuthority(Role.ADMIN.toString(), Role.STANDARD.toString())
                .anyRequest()
                .authenticated();
    }
}

