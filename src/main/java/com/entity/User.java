package com.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Table("tbuser")
public class User {
    
    public enum Role {
        ADMIN,
        USER
    }

    @Id
    @JsonProperty
    private Long id;

    @JsonProperty
    private String email;

    @JsonProperty
    private int passwordHash;

    @JsonProperty
    private Role role;
    

    public User(String email, Role role, int passwordHash) {
        this.email = email;
        this.role = role;
        this.passwordHash = passwordHash;
    }

    public Long getId() {
        return id;
    }
    
    public String getEmail() {
        return email;
    }

    public int getPasswordHash() {
        return passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setEmail(String name) {
        this.email = name;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @PersistenceCreator
    private User(Long id, String email, Role role, int passwordHash) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.passwordHash = passwordHash;
    }


}
