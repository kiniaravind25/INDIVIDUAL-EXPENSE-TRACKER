package com.project.expenseTracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles", // Join Table
        joinColumns = @JoinColumn(name = "user_id"),         // FK to users
        inverseJoinColumns = @JoinColumn(name = "role_id")   // FK to roles
    )
    private Set<Role> roles = new HashSet<>();


}
