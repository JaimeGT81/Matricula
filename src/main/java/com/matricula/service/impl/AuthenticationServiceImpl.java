package com.matricula.service.impl;

import com.matricula.dto.LoginForm;
import com.matricula.entity.UserAccount;
import com.matricula.repository.UserRepository;
import com.matricula.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean authenticate(LoginForm form) {
        return userRepository.findByUserId(form.getUsername())
                .map(user -> user.getUserPassword().equals(form.getPassword()))
                .orElse(false);
    }
}