package com.vibecraftcrew.projectmanagement.project;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMembershipRepository membershipRepository;

    public ProjectService(ProjectRepository pr, ProjectMembershipRepository mr){ this.projectRepository = pr; this.membershipRepository = mr; }

    public ProjectDto create(ProjectCreateDto dto, UUID creatorId){
        Project p = new Project();
        p.setName(dto.name());
        p.setDescription(dto.description());
        p.setStartDate(dto.startDate());
        p.setEndDate(dto.endDate());
        projectRepository.save(p);
        // add membership for creator as owner
        ProjectMembership owner = new ProjectMembership();
        owner.setProject(p);
        owner.setUserId(creatorId);
        owner.setRole("Manager");
        owner.setOwner(true);
        membershipRepository.save(owner);
        p.getMembers().add(owner);
        projectRepository.save(p);
        return toDto(p);
    }

    public ProjectDto toDto(Project p){
        Set<ProjectMemberDto> members = membershipRepository.findByProjectId(p.getId()).stream().map(m -> new ProjectMemberDto(m.getUserId(), m.getRole(), m.isOwner())).collect(Collectors.toSet());
        return new ProjectDto(p.getId(), p.getName(), p.getDescription(), p.getStartDate(), p.getEndDate(), members);
    }

    public List<ProjectDto> listAll(){
        return projectRepository.findAll().stream().map(this::toDto).toList();
    }

    public ProjectDto addMember(UUID projectId, UUID userId, String role, boolean isOwner){
        var p = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
        var existing = membershipRepository.findByProjectIdAndUserId(projectId, userId);
        if (existing.isPresent()) throw new RuntimeException("User already member");
        ProjectMembership m = new ProjectMembership();
        m.setProject(p);
        m.setUserId(userId);
        m.setRole(role);
        m.setOwner(isOwner);
        membershipRepository.save(m);
        return toDto(p);
    }
}