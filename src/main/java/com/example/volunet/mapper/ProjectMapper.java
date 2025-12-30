package com.example.volunet.mapper;

import com.example.volunet.dto.projectDtos.ProjectInputDto;
import com.example.volunet.dto.projectDtos.ProjectOutputDto;
import com.example.volunet.entity.Organization;
import com.example.volunet.entity.Project;
import com.example.volunet.entity.Volunteer;
import com.example.volunet.exceptions.ResourceNotFoundException;
import com.example.volunet.repository.OrganizationRepository;
import com.example.volunet.repository.VolunteerRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProjectMapper {
    private final OrganizationRepository organizationRepository;
    private final VolunteerRepository volunteerRepository;
    private final VolunteerMapper volunteerMapper;

    public ProjectMapper(OrganizationRepository organizationRepository, VolunteerRepository volunteerRepository, VolunteerMapper volunteerMapper) {
        this.organizationRepository = organizationRepository;
        this.volunteerRepository = volunteerRepository;
        this.volunteerMapper = volunteerMapper;
    }

    public Project toProjectEntity(ProjectInputDto projectInputDto) {
        if (projectInputDto == null) return null;
        Organization organization = organizationRepository.findById(projectInputDto.getOrganization_id())
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));

        Set<Volunteer> volunteers = new HashSet<>();
        if (projectInputDto.getVolunteerIds() != null && !projectInputDto.getVolunteerIds().isEmpty()) {
            volunteers = new HashSet<>(volunteerRepository.findAllById(projectInputDto.getVolunteerIds()));
        }

        return Project.builder()
                .title(projectInputDto.getTitle())
                .description(projectInputDto.getDescription())
                .deadline(projectInputDto.getDeadline())
                .requiredSkill(projectInputDto.getRequiredSkill())
                .status(projectInputDto.getStatus())
                .organization(organization)
                .volunteers(volunteers)
                .build();
    }

    public ProjectOutputDto toProjectOutputDto(Project project) {
        if (project == null) return null;
        return ProjectOutputDto.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .deadline(project.getDeadline())
                .requiredSkill(project.getRequiredSkill())
                .status(project.getStatus())
                .organization(project.getOrganization().getName())
                .volunteers(project.getVolunteers().stream()
                        .map(volunteerMapper::toVolunteerOutputDto).collect(Collectors.toSet()))
                .build();
    }
}
