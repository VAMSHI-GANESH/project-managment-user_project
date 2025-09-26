package com.vibecraftcrew.projectmanagement.project;

import java.time.LocalDate;

public record ProjectCreateDto(String name, String description, LocalDate startDate, LocalDate endDate) {}