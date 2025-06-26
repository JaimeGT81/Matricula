package com.matricula.service.impl;

import com.matricula.entity.UserAccount;
import com.matricula.repository.UserRepository;
import com.matricula.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepo;
    @Override public Page<UserAccount> listAll(Pageable p) {
        return userRepo.findAll(p);
    }
    @Override public void save(UserAccount u){ userRepo.save(u); }
    @Override public Optional<UserAccount> findById(String id){
        return userRepo.findById(id);
    }
    @Override public void delete(String id){ userRepo.deleteById(id); }
}
