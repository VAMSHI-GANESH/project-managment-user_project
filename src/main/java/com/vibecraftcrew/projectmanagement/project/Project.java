package com.vibecraftcrew.projectmanagement.project;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.*;
import java.util.*;

@Entity
@Table(name = "projects")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Project {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    @Column(length = 2000)
    private String description;

    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectMembership> members = new HashSet<>();

    // status, visibility, metadata
}
