package com.group15.jacksonpollock.security;

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
            "/paintDashboard/total",
            "/paintDashboard/totalUncommissioned",
            "/paintDashboard/lowestUptakeArea",
            "/paintDashboard/averagePercentageUptake",
            "/paintDashboard/acceptableUptake",
            "/paintMap/commissioned",
            "/paintMap/uptake"
    };
    
    @Override
    public void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authz,
                          HttpSecurity httpSecurity) throws Exception {
        authz.requestMatchers(endpoints)
                .hasAnyAuthority(Role.ADMIN.toString(), Role.STANDARD.toString())
                .anyRequest()
                .authenticated();

        httpSecurity.cors(Customizer.withDefaults());
    }
}
