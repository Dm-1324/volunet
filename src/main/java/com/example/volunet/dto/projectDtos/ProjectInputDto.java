package com.example.volunet.dto.projectDtos;

import com.example.volunet.entity.enums.ProjectStatus;
import com.example.volunet.entity.enums.SkillType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class ProjectInputDto {
    @NotNull(message = "Title cannot be empty")
    private String title;

    @NotNull(message = "Description cannot be empty")
    private String description;

    @NotNull(message = "DeadLine cannot be empty")
    private LocalDateTime deadline;

    private SkillType requiredSkill;

    private ProjectStatus status;

    private Long organization_id;

    private Set<Long> volunteerIds;
}
