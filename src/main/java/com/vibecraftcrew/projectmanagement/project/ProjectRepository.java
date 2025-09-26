package com.vibecraftcrew.projectmanagement.project;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    // add queries like findByMembers_userId if needed
}
