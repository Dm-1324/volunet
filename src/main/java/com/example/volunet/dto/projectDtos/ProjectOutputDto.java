package com.example.volunet.dto.projectDtos;

import com.example.volunet.dto.volunteerDtos.VolunteerOutputDto;
import com.example.volunet.entity.enums.ProjectStatus;
import com.example.volunet.entity.enums.SkillType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class ProjectOutputDto {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime deadline;
    private SkillType requiredSkill;
    private ProjectStatus status;
    private String organization;
    private Set<VolunteerOutputDto> volunteers;

}
