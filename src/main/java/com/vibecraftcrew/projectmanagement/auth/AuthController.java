package com.vibecraftcrew.projectmanagement.auth;

import com.vibecraftcrew.projectmanagement.security.JwtUtils;
import com.vibecraftcrew.projectmanagement.user.UserDto;
import com.vibecraftcrew.projectmanagement.user.UserRepository;
import com.vibecraftcrew.projectmanagement.user.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public AuthController(UserService us, JwtUtils ju, UserRepository ur, PasswordEncoder pe){ this.userService = us; this.jwtUtils = ju; this.userRepository = ur; this.passwordEncoder = pe; }


    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterRequest req){
        var dto = userService.register(req);
        return ResponseEntity.ok(dto);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req){
        var user = userRepository.findByEmail(req.email()).orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!passwordEncoder.matches(req.password(), user.getPassword())) throw new RuntimeException("Invalid credentials");
        String token = jwtUtils.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(token, "Bearer"));
    }
}