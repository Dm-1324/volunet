package com.example.volunet.mapper;

import com.example.volunet.dto.volunteerDtos.VolunteerInputDto;
import com.example.volunet.dto.volunteerDtos.VolunteerOutputDto;
import com.example.volunet.entity.Volunteer;
import com.example.volunet.entity.enums.AvailabilityStatus;
import org.springframework.stereotype.Component;

@Component
public class VolunteerMapper {
    public Volunteer toVolunteerEntity(VolunteerInputDto volunteerInputDto) {
        if (volunteerInputDto == null) return null;
        return Volunteer.builder()
                .name(volunteerInputDto.getName())
                .email(volunteerInputDto.getEmail())
                .bio(volunteerInputDto.getBio())
                .availabilityStatus(AvailabilityStatus.ACTIVE)
                .build();
    }

    public VolunteerOutputDto toVolunteerOutputDto(Volunteer volunteer) {
        if (volunteer == null) return null;
        return VolunteerOutputDto.builder()
                .id(volunteer.getId())
                .name(volunteer.getName())
                .email(volunteer.getEmail())
                .bio(volunteer.getBio())
                .availabilityStatus(volunteer.getAvailabilityStatus())
                .build();
    }
}
