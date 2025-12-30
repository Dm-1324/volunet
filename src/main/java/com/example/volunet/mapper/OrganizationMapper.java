package com.example.volunet.mapper;

import com.example.volunet.dto.organizationDtos.OrganizationInputDto;
import com.example.volunet.dto.organizationDtos.OrganizationOutputDto;
import com.example.volunet.entity.Organization;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper {

    public Organization toOrganizationEntity(OrganizationInputDto organizationInputDto) {
        if (organizationInputDto == null) return null;
        return Organization.builder()
                .name(organizationInputDto.getName())
                .email(organizationInputDto.getEmail())
                .description(organizationInputDto.getDescription())
                .location(organizationInputDto.getLocation())
                .verifiedStatus(organizationInputDto.getVerifiedStatus())
                .build();
    }

    public OrganizationOutputDto toOrganizationOutputDto(Organization organization) {
        if (organization == null) return null;
        return OrganizationOutputDto.builder()
                .id(organization.getId())
                .name(organization.getName())
                .email(organization.getEmail())
                .description(organization.getDescription())
                .location(organization.getLocation())
                .verifiedStatus(organization.getVerifiedStatus())
                .build();
    }
}
