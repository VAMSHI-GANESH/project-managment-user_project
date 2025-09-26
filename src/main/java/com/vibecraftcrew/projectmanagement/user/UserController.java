package com.vibecraftcrew.projectmanagement.user;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService us){ this.userService = us; }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public List<UserDto> list(){ return userService.listAll(); }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER') or #id == principal.id")
    public UserDto get(@PathVariable UUID id){ return userService.findById(id).orElseThrow(() -> new RuntimeException("Not found")); }

    @PostMapping("/{id}/roles")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MANAGER')")
    public UserDto addRole(@PathVariable UUID id, @RequestParam String role){ return userService.addRole(id, role); }
}
