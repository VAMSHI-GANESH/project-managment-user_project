package com.vibecraftcrew.projectmanagement.security;

import com.vibecraftcrew.projectmanagement.user.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository repo) {
        this.userRepository = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return map(user);
    }

    public UserDetails loadUserById(UUID id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
        return map(user);
    }

    private UserDetails map(com.vibecraftcrew.projectmanagement.user.User u) {
        var authorities = u.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // ✅ prefix role
                .toList();

        return org.springframework.security.core.userdetails.User
                .withUsername(u.getEmail())
                .password(u.getPassword())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!u.isActive())
                .build();
    }
}
