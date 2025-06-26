package com.matricula.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Role")
public class Role {
    @Id
    @Column(length = 50)
    private String name;

    @OneToMany(mappedBy = "role")
    private Set<UserAccount> users;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserAccount> getUsers() {
        return users;
    }

    public void setUsers(Set<UserAccount> users) {
        this.users = users;
    }
}
