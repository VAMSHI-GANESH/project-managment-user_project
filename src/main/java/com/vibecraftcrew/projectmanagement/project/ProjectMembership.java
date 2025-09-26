package com.vibecraftcrew.projectmanagement.project;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;

@Entity
@Table(name = "project_memberships",
       uniqueConstraints = @UniqueConstraint(columnNames = {"project_id","user_id"}))
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class ProjectMembership {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "user_id", nullable = false)
    private UUID userId; // keep as UUID to avoid circular mapping in minimal design

    private String role; // Frontend, Backend, QA, Manager

    private boolean isOwner = false; // project owner/creator
}