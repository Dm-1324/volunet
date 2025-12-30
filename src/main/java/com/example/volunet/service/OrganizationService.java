package com.example.volunet.service;

import com.example.volunet.dto.organizationDtos.OrganizationInputDto;
import com.example.volunet.dto.organizationDtos.OrganizationNpoDto;
import com.example.volunet.dto.organizationDtos.OrganizationOutputDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrganizationService {

    OrganizationOutputDto createOrganization(OrganizationInputDto organizationInputDto);

    List<OrganizationOutputDto> getOrganizations();

    OrganizationNpoDto getOrganizationNpoData(Long organizationId);
}


