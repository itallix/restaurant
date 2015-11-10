package org.itallix.restaurant.security;

import org.itallix.restaurant.dao.repository.AccountRepository;
import org.itallix.restaurant.models.persistence.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class RestaurantAuthenticationProvider implements AuthenticationProvider {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AccountRepository accountRepository;

    @Override
    @Transactional(readOnly = true)
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication.getPrincipal() == null || authentication.getCredentials() == null) {
            logger.info("invalid {}", authentication);
            throw new BadCredentialsException("null username or password");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Optional<Account> client = accountRepository.findByLogin(authentication.getName());
        Account rightAccount = client.orElseThrow(() -> new BadCredentialsException("user not found"));
        String passwordHash = client.get().getPasswordHash();
        boolean matches = bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), passwordHash);
        if (!matches) {
            throw new BadCredentialsException("wrong password");
        }
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        UserDetails user = new User(rightAccount.getLogin(), "", Collections.unmodifiableCollection(authorities));
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
