package com.vibecraftcrew.projectmanagement.project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface ProjectMembershipRepository extends JpaRepository<ProjectMembership, UUID> {
    List<ProjectMembership> findByUserId(UUID userId);
    List<ProjectMembership> findByProjectId(UUID projectId);
    Optional<ProjectMembership> findByProjectIdAndUserId(UUID projectId, UUID userId);
}

