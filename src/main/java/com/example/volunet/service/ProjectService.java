package com.example.volunet.service;

import com.example.volunet.dto.projectDtos.ProjectInputDto;
import com.example.volunet.dto.projectDtos.ProjectOutputDto;
import com.example.volunet.entity.enums.ProjectStatus;
import com.example.volunet.entity.enums.SkillType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectService {
    ProjectOutputDto createProject(ProjectInputDto projectInputDto);

    List<ProjectOutputDto> getProject();

    ProjectOutputDto assignVolunteerToProject(Long projectId, Long volunteerId);

    void updateProjectStatus(Long projectId);

    Page<ProjectOutputDto> filterProjects(SkillType skill, ProjectStatus status, Pageable pageable);

}
