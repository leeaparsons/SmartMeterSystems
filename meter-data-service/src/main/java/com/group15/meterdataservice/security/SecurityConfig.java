package com.group15.meterdataservice.security;

import com.fortress.security.entity.Role;
import com.fortress.security.security.FortressConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends FortressConfiguration {

    private final  String[] endpoints = {
            "/uncommissioned",
            "/devices/**",
    };

    @Override
    public void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authz,
                          HttpSecurity http) throws Exception {
        authz.requestMatchers(endpoints)
                .hasAnyAuthority(Role.ADMIN.toString(), Role.STANDARD.toString())
                .anyRequest()
                .authenticated();
        http.cors(Customizer.withDefaults());
    }
}
