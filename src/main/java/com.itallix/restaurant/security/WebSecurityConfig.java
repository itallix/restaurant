package com.itallix.restaurant.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@ComponentScan(basePackages = "com.itallix.restaurant.filter")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers(
                        "/favicon.ico",
                        "/index.html",
                        "/debug.html",
                        "/auth/**/*",
                        "/debug/**/*",
                        "/css/**/*",
                        "/js/**/*",
                        "/img/**/*",
                        "/")
                .permitAll()
                .anyRequest()
                .authenticated().and()
                .logout().and()
//                .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
                .csrf().disable();
    }

//    private CsrfTokenRepository csrfTokenRepository() {
//        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
//        repository.setHeaderName("X-XSRF-TOKEN");
//        return repository;
//    }

    @Bean
    public CurrentUser currentUserIdContext() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                return Optional.empty();
            }
            String name = authentication.getName();
            try {
                return Optional.of(name);
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
        };
    }

    @Bean
    public AuthenticationProvider authenticationProviderBean() {
        return new RestaurantAuthenticationProvider();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProviderBean());
    }
}
