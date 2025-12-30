package com.example.volunet.dto.organizationDtos;

import com.example.volunet.entity.enums.VerifiedStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrganizationOutputDto {

    private Long id;
    private String name;
    private String email;
    private String description;
    private String location;
    private VerifiedStatus verifiedStatus;
}
