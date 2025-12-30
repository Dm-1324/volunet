package com.example.volunet.service;

import com.example.volunet.dto.organizationDtos.*;
import com.example.volunet.entity.Organization;
import com.example.volunet.entity.Project;
import com.example.volunet.entity.Volunteer;
import com.example.volunet.entity.enums.ProjectStatus;
import com.example.volunet.exceptions.DuplicateResourceException;
import com.example.volunet.exceptions.ResourceNotFoundException;
import com.example.volunet.mapper.OrganizationMapper;
import com.example.volunet.repository.OrganizationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationMapper organizationMapper;
    private final OrganizationRepository organizationRepository;

    public OrganizationServiceImpl(OrganizationMapper organizationMapper, OrganizationRepository organizationRepository) {
        this.organizationMapper = organizationMapper;
        this.organizationRepository = organizationRepository;
    }

    @Override
    public OrganizationOutputDto createOrganization(OrganizationInputDto organizationInputDto) {
        if (organizationRepository.existsByNameOrEmailCaseSensitive(organizationInputDto.getName(),
                organizationInputDto.getEmail()) == 1L) {
            if (organizationRepository.existsByNameCaseSensitive(organizationInputDto.getName()) == 1L) {
                throw new DuplicateResourceException("An organization with the same name already exists: " + organizationInputDto.getName());
            }
            if (organizationRepository.existsByEmailCaseSensitive(organizationInputDto.getEmail()) == 1L) {
                throw new DuplicateResourceException("An organization with the same email already exists: " + organizationInputDto.getEmail());
            }
        }
        Organization newOrganization = organizationMapper.toOrganizationEntity(organizationInputDto);
        organizationRepository.save(newOrganization);

        return organizationMapper.toOrganizationOutputDto(newOrganization);
    }

    @Override
    public List<OrganizationOutputDto> getOrganizations() {
        return organizationRepository.findAll().stream()
                .map(organizationMapper::toOrganizationOutputDto)
                .toList();
    }

    @Override
    public OrganizationNpoDto getOrganizationNpoData(Long organizationId) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found with id: " + organizationId));

        long totalImpactHours = organization.getProjects().stream()
                .filter(p -> p.getStatus() == ProjectStatus.COMPLETED)
                .mapToLong(p -> 10)
                .sum();

        long completedProjectCount =
                organization.getProjects().stream()
                        .filter(p -> p.getStatus() == ProjectStatus.COMPLETED)
                        .count();

        long activeVolunteersCount = organization.getProjects().stream()
                .flatMap(p -> p.getVolunteers().stream())
                .map(Volunteer::getId)
                .distinct()
                .count();

        LocalDateTime lastProjectCompletedAt =
                organization.getProjects().stream()
                        .filter(p -> p.getStatus() == ProjectStatus.COMPLETED)
                        .map(Project::getDeadline)
                        .max(LocalDateTime::compareTo)
                        .orElse(null);

        List<SkillBreakDownDto> skillBreakDown = organization.getProjects().
                stream()
                .filter(p -> p.getStatus() == ProjectStatus.COMPLETED)
                .collect(Collectors.groupingBy(Project::getRequiredSkill, Collectors.counting()))
                .entrySet().stream()
                .map(e -> SkillBreakDownDto.builder()
                        .skill(e.getKey())
                        .count(e.getValue())
                        .build())
                .toList();


        ImpactMetricsDto metrics = ImpactMetricsDto.builder()
                .totalImpactHours(totalImpactHours)
                .completedProjectCount(completedProjectCount)
                .activeVolunteersCount(activeVolunteersCount)
                .lastProjectCompletedAt(lastProjectCompletedAt)
                .build();

        return OrganizationNpoDto.builder()
                .organizationId(organizationId)
                .organizationName(organization.getName())
                .impactMetrics(metrics)
                .skillBreakDown(skillBreakDown)
                .build();
    }
}
