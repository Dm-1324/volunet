package com.example.volunet.service;

import com.example.volunet.dto.volunteerDtos.VolunteerInputDto;
import com.example.volunet.dto.volunteerDtos.VolunteerOutputDto;
import com.example.volunet.entity.Volunteer;
import com.example.volunet.entity.enums.AvailabilityStatus;
import com.example.volunet.exceptions.ResourceNotFoundException;
import com.example.volunet.mapper.VolunteerMapper;
import com.example.volunet.repository.VolunteerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolunteerServiceImpl implements VolunteerService {
    private final VolunteerMapper volunteerMapper;
    private final VolunteerRepository volunteerRepository;
    private final PasswordEncoder passwordEncoder;

    public VolunteerServiceImpl(VolunteerMapper volunteerMapper, VolunteerRepository volunteerRepository, PasswordEncoder passwordEncoder) {
        this.volunteerMapper = volunteerMapper;
        this.volunteerRepository = volunteerRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public VolunteerOutputDto createVolunteer(VolunteerInputDto volunteerInputDto) {
        Volunteer newVolunteer = volunteerMapper.toVolunteerEntity(volunteerInputDto);
        newVolunteer.setPassword(passwordEncoder.encode(newVolunteer.getPassword()));
        volunteerRepository.save(newVolunteer);
        return volunteerMapper.toVolunteerOutputDto(newVolunteer);
    }

    @Override
    public List<VolunteerOutputDto> getVolunteer() {
        return volunteerRepository.findAll()
                .stream().map(volunteerMapper::toVolunteerOutputDto)
                .toList();
    }

    @Override
    public void updateVolunteerStatusToBusy(Long volunteerId) {
        Volunteer volunteer = volunteerRepository.findById(volunteerId)
                .orElseThrow(() -> new ResourceNotFoundException("Volunteer not found with id: " + volunteerId));

        volunteer.setAvailabilityStatus(AvailabilityStatus.BUSY);
        volunteerRepository.save(volunteer);
    }
}
