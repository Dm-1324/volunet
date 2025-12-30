package com.example.volunet.dto.volunteerDtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VolunteerInputDto {
    @NotNull(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Email cannot be empty")
    @Email(message = "Invalid Email Format")
    private String email;

    @NotNull(message = "Bio cannot be empty")
    private String bio;
}
