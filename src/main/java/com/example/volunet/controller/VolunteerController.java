package com.example.volunet.controller;

import com.example.volunet.dto.volunteerDtos.VolunteerInputDto;
import com.example.volunet.dto.volunteerDtos.VolunteerOutputDto;
import com.example.volunet.service.VolunteerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/volunteer")
@RequiredArgsConstructor
public class VolunteerController {
    private final VolunteerService volunteerService;

    @PostMapping
    public ResponseEntity<VolunteerOutputDto> createVolunteer(@Valid @RequestBody VolunteerInputDto volunteerInputDto) {
        return new ResponseEntity<>(volunteerService.createVolunteer(volunteerInputDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VolunteerOutputDto>> getAllVolunteer() {
        return ResponseEntity.ok(volunteerService.getVolunteer());
    }

    @PatchMapping("/{volunteerId}/status")
    public ResponseEntity<String> updateVolunteerStatusToBusy(@PathVariable Long volunteerId) {
        volunteerService.updateVolunteerStatusToBusy(volunteerId);
        return ResponseEntity.ok("Volunteer Status Updated Successfully");
    }
}
