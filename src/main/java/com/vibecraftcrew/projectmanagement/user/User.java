package com.vibecraftcrew.projectmanagement.user;

import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password; // hashed

    private String fullName;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>(); // e.g., ROLE_ADMIN, ROLE_MANAGER, ROLE_MEMBER

    // convenience fields
    private boolean active = true;

    // timestamps (omitted getters/setters for brevity)
}
