package com.matricula.service;

import com.matricula.entity.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    Page<UserAccount> listAll(Pageable p);
    void save(UserAccount u);
    Optional<UserAccount> findById(String id);
    void delete(String id);
}
