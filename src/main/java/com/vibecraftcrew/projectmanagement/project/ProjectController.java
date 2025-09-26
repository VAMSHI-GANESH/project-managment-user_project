package com.vibecraftcrew.projectmanagement.project;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;
    public ProjectController(ProjectService ps){ this.projectService = ps; }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public ProjectDto create(@RequestBody ProjectCreateDto dto, @RequestHeader("X-User-Id") UUID creatorId){
        // In real app, extract from security principal
        return projectService.create(dto, creatorId);
    }

    @GetMapping
    public List<ProjectDto> list(){ return projectService.listAll(); }

    @PostMapping("/{id}/members")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public ProjectDto addMember(@PathVariable UUID id, @RequestParam UUID userId, @RequestParam String role){
        return projectService.addMember(id, userId, role, false);
    }
}
