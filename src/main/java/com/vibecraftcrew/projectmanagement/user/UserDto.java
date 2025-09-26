package com.vibecraftcrew.projectmanagement.user;

import java.util.UUID;
import java.util.Set;

// User DTOs
public record UserDto(UUID id, String email, String fullName, Set<String> roles, boolean active) {}