package com.example.volunet.dto.organizationDtos;

import com.example.volunet.entity.enums.SkillType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SkillBreakDownDto {
    private SkillType skill;
    private Long count;
}
