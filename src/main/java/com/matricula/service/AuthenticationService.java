package com.matricula.service;

import com.matricula.dto.LoginForm;

public interface AuthenticationService {
    /**
     * Valida credenciales.
     */
    boolean authenticate(LoginForm form);
}