package com.vibecraftcrew.projectmanagement.project;

import java.util.UUID;

public record ProjectMemberDto(UUID userId, String role, boolean isOwner) {}