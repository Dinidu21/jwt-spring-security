package com.example.practice_add.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;  // e.g., ROLE_USER, ROLE_ADMIN

    public Role() {}
    public Role(String name) {
        this.name = name;
    }
}