package com.example.volunet.controller;

import com.example.volunet.dto.organizationDtos.OrganizationInputDto;
import com.example.volunet.dto.organizationDtos.OrganizationNpoDto;
import com.example.volunet.dto.organizationDtos.OrganizationOutputDto;
import com.example.volunet.service.OrganizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organization")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;

    @PostMapping
    public ResponseEntity<OrganizationOutputDto> createOrganization(@Valid @RequestBody OrganizationInputDto organizationInputDto) {
        return new ResponseEntity<>(organizationService.createOrganization(organizationInputDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrganizationOutputDto>> getAllOrganizations() {
        return ResponseEntity.ok(organizationService.getOrganizations());
    }

    @GetMapping("{organizationId}/impact")
    public ResponseEntity<OrganizationNpoDto> getOrganizationNpoData(@PathVariable Long organizationId) {
        return ResponseEntity.ok(organizationService.getOrganizationNpoData(organizationId));
    }
}
