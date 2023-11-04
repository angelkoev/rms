package com.rms.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_roles")
public class UserRoleEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private UserRoleEnum role;
    @Column(nullable = false)
    private String description;

    public UserRoleEntity() {
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum name) {
        this.role = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
