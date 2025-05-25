package com.matricula.service.impl;

import com.matricula.entity.UserAccount;
import com.matricula.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public CustomUserDetailsService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        UserAccount ua = repo.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("No existe " + username));
        return User.withUsername(ua.getUserId())
                .password(ua.getUserPassword())
                .roles("USER")
                .build();
    }
}