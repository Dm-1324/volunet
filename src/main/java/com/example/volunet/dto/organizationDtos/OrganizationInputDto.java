package com.example.volunet.dto.organizationDtos;

import com.example.volunet.entity.enums.VerifiedStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrganizationInputDto {
    @NotNull(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Email cannot be empty")
    @Email(message = "Invalid Email format")
    private String email;

    @NotNull(message = "Description cannot be empty")
    private String description;

    @NotNull(message = "Location cannot be empty")
    private String location;

    private VerifiedStatus verifiedStatus;
}
