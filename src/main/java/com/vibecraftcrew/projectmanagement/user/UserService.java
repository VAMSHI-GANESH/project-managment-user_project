package com.vibecraftcrew.projectmanagement.user;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vibecraftcrew.projectmanagement.auth.RegisterRequest;


import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository ur, PasswordEncoder pe){ this.userRepository = ur; this.passwordEncoder = pe; }

    public UserDto register(RegisterRequest req){
        if (userRepository.existsByEmail(req.email())) throw new RuntimeException("Email already exists");
        User u = new User();
        u.setEmail(req.email());
        u.setPassword(passwordEncoder.encode(req.password()));
        u.setFullName(req.fullName());
        if (req.roles() != null && !req.roles().isEmpty()) {
            u.getRoles().addAll(req.roles());
        } else {
            u.getRoles().add("ROLE_MEMBER");
        }
        userRepository.save(u);
        return new UserDto(u.getId(), u.getEmail(), u.getFullName(), u.getRoles(), u.isActive());
    }

    public Optional<UserDto> findById(UUID id){
        return userRepository.findById(id).map(u -> new UserDto(u.getId(), u.getEmail(), u.getFullName(), u.getRoles(), u.isActive()));
    }

    public List<UserDto> listAll(){
        return userRepository.findAll().stream().map(u -> new UserDto(u.getId(), u.getEmail(), u.getFullName(), u.getRoles(), u.isActive())).toList();
    }

    public UserDto addRole(UUID userId, String role){
        var user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.getRoles().add(role);
        userRepository.save(user);
        return new UserDto(user.getId(), user.getEmail(), user.getFullName(), user.getRoles(), user.isActive());
    }
}
