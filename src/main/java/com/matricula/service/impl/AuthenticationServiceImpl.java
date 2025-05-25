package com.matricula.service.impl;

import com.matricula.dto.LoginForm;
import com.matricula.entity.UserAccount;
import com.matricula.repository.UserRepository;
import com.matricula.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean authenticate(LoginForm form) {
        logger.info("Entering authenticate method with username: {}", form.getUsername());
        return userRepository.findByUserId(form.getUsername())
                .map(user -> {
                    logger.info("Comparing passwords: raw={} encrypted={}", form.getPassword(), user.getUserPassword());
                    return passwordEncoder.matches(form.getPassword(), user.getUserPassword());
                })
                .orElse(false);
    }
}