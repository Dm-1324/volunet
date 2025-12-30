package com.example.volunet.controller;

import com.example.volunet.dto.page.PaginatedResponse;
import com.example.volunet.dto.projectDtos.ProjectInputDto;
import com.example.volunet.dto.projectDtos.ProjectOutputDto;
import com.example.volunet.entity.enums.ProjectStatus;
import com.example.volunet.entity.enums.SkillType;
import com.example.volunet.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectOutputDto> createProject(@Valid @RequestBody ProjectInputDto projectInputDto) {
        return new ResponseEntity<>(projectService.createProject(projectInputDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProjectOutputDto>> getAllProject() {
        return ResponseEntity.ok(projectService.getProject());
    }

    @PostMapping("/{projectId}/assign/{volunteerId}")
    public ResponseEntity<ProjectOutputDto> assignVolunteerToProject(@PathVariable Long projectId, @PathVariable Long volunteerId) {
        return new ResponseEntity<>(projectService.assignVolunteerToProject(projectId, volunteerId), HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{projectId}/status")
    public ResponseEntity<String> updateProjectStatus(@PathVariable Long projectId) {
        projectService.updateProjectStatus(projectId);
        return ResponseEntity.ok("Project Status Updated Successfully for id: " + projectId);
    }

    @GetMapping("/filter")
    public ResponseEntity<PaginatedResponse<ProjectOutputDto>> getFilteredProjects(
            @RequestParam SkillType skill,
            @RequestParam ProjectStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProjectOutputDto> projectOutputDtoPage = projectService.filterProjects(skill, status, pageable);
        return ResponseEntity.ok(new PaginatedResponse<>(projectOutputDtoPage));
    }
}
