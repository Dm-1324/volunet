package com.example.volunet.dto.organizationDtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ImpactMetricsDto {
    private Long totalImpactHours;
    private Long completedProjectCount;
    private Long activeVolunteersCount;
    private LocalDateTime lastProjectCompletedAt;
}
