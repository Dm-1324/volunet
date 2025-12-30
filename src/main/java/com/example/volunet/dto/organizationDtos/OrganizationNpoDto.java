package com.example.volunet.dto.organizationDtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrganizationNpoDto {
    private Long organizationId;
    private String organizationName;
    private ImpactMetricsDto impactMetrics;
    private List<SkillBreakDownDto> skillBreakDown;
}
