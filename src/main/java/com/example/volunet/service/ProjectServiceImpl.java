package com.example.volunet.service;

import com.example.volunet.dto.projectDtos.ProjectInputDto;
import com.example.volunet.dto.projectDtos.ProjectOutputDto;
import com.example.volunet.entity.Project;
import com.example.volunet.entity.Volunteer;
import com.example.volunet.entity.enums.ProjectStatus;
import com.example.volunet.entity.enums.SkillType;
import com.example.volunet.exceptions.InvalidDataException;
import com.example.volunet.exceptions.ResourceNotFoundException;
import com.example.volunet.mapper.ProjectMapper;
import com.example.volunet.repository.ProjectRepository;
import com.example.volunet.repository.VolunteerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectMapper projectMapper;
    private final ProjectRepository projectRepository;
    private final VolunteerRepository volunteerRepository;

    public ProjectServiceImpl(ProjectMapper projectMapper, ProjectRepository projectRepository,
                              VolunteerRepository volunteerRepository) {
        this.projectMapper = projectMapper;
        this.projectRepository = projectRepository;
        this.volunteerRepository = volunteerRepository;
    }

    @Override
    public ProjectOutputDto createProject(ProjectInputDto projectInputDto) {
        if (projectInputDto.getDeadline().isBefore(LocalDateTime.now())) {
            throw new InvalidDataException("The deadline should be in future");
        }
        Project newProject = projectMapper.toProjectEntity(projectInputDto);
        projectRepository.save(newProject);
        return projectMapper.toProjectOutputDto(newProject);
    }

    @Override
    public List<ProjectOutputDto> getProject() {
        return projectRepository.findAll().stream()
                .map(projectMapper::toProjectOutputDto)
                .toList();
    }

    @Override
    public ProjectOutputDto assignVolunteerToProject(Long projectId, Long volunteerId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + projectId));

        Volunteer volunteer = volunteerRepository.findById(volunteerId)
                .orElseThrow(() -> new ResourceNotFoundException("Volunteer not found with id: " + volunteerId));
        long projectInProgressCount =
                volunteer.getProjects().stream().filter(p -> p.getStatus() == ProjectStatus.IN_PROGRESS).count();
        if (projectInProgressCount >= 3L) {
            throw new InvalidDataException
                    ("Volunteer should have less than 3 IN PROGRESS task. Volunteer " + volunteer.getName() + " has " + projectInProgressCount + " IN PROGRESS tasks");
        }
        project.getVolunteers().add(volunteer);
        projectRepository.save(project);
        return projectMapper.toProjectOutputDto(project);
    }

    @Override
    public void updateProjectStatus(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + projectId));
        if (project.getStatus() == ProjectStatus.DRAFT) {
            project.setStatus(ProjectStatus.RECRUITING);
        } else if (project.getStatus() == ProjectStatus.RECRUITING) {
            project.setStatus(ProjectStatus.IN_PROGRESS);
        } else if (project.getStatus() == ProjectStatus.IN_PROGRESS) {
            project.setStatus(ProjectStatus.COMPLETED);
        }
        projectRepository.save(project);
    }

    @Override
    public Page<ProjectOutputDto> filterProjects(SkillType skill, ProjectStatus status, Pageable pageable) {
        String skillRequest = (skill != null) ? skill.name() : null;
        String statusRequest = (status != null) ? status.name() : null;

        Page<Project> projectPage = projectRepository.filterProjects(skillRequest, statusRequest, pageable);

        return projectPage.map(projectMapper::toProjectOutputDto);
    }
}
