package com.example.volunet.dto.volunteerDtos;

import com.example.volunet.entity.enums.AvailabilityStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VolunteerOutputDto {
    private Long id;
    private String name;
    private String email;
    private String bio;
    private AvailabilityStatus availabilityStatus;
}
