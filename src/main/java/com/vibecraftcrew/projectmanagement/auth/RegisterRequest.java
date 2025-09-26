package com.vibecraftcrew.projectmanagement.auth;

import java.util.Set;

// Auth DTOs
public record RegisterRequest(String email, String password, String fullName, Set<String> roles) {}


