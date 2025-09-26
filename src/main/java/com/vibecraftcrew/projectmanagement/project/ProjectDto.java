package com.vibecraftcrew.projectmanagement.project;

import java.time.LocalDate;
import java.util.UUID;
import java.util.Set;

// Project DTOs
public record ProjectDto(UUID id, String name, String description, LocalDate startDate, LocalDate endDate, Set<ProjectMemberDto> members) {}

