package com.matricula.controller;

import com.matricula.entity.Role;
import com.matricula.entity.UserAccount;
import com.matricula.repository.RoleRepository;
import com.matricula.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/config")
public class ConfiguracionController {
    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public String listUsers(Model m,
                            @RequestParam(defaultValue="0") int page){
        Page<UserAccount> p = userService.listAll(PageRequest.of(page,10));
        m.addAttribute("users", p);
        m.addAttribute("roles", roleRepo.findAll());
        m.addAttribute("newUser", new UserAccount());
        return "users";
    }

    @PostMapping("/users")
    public String createOrUpdate(@ModelAttribute UserAccount u,
                                 @RequestParam String selectedRole) {
        Optional<UserAccount> existingUser = userService.findById(u.getUserId());

        if (existingUser.isPresent()) {
            // Update existing user
            UserAccount current = existingUser.get();
            current.setUserEmail(u.getUserEmail());
            current.setRole(roleRepo.getById(selectedRole));

            // Only update password if a new one is provided
            if (u.getUserPassword() != null && !u.getUserPassword().isEmpty()) {
                current.setUserPassword(passwordEncoder.encode(u.getUserPassword()));
            }

            userService.save(current);
        } else {
            // Create new user
            u.setRole(roleRepo.getById(selectedRole));
            u.setUserPassword(passwordEncoder.encode(u.getUserPassword()));
            u.setFechaRegistro(LocalDateTime.now());
            u.setFechaConexion(LocalDateTime.now());
            userService.save(u);
        }

        return "redirect:/config/users";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable String id){
        userService.delete(id);
        return "redirect:/config/users";
    }
}
