package com.example.volunet.service;

import com.example.volunet.dto.volunteerDtos.VolunteerInputDto;
import com.example.volunet.dto.volunteerDtos.VolunteerOutputDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VolunteerService {
    VolunteerOutputDto createVolunteer(VolunteerInputDto volunteerInputDto);

    List<VolunteerOutputDto> getVolunteer();

    void updateVolunteerStatusToBusy(Long volunteerId);
}
