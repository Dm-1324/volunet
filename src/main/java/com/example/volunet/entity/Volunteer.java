package com.example.volunet.entity;

import com.example.volunet.entity.enums.AvailabilityStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "volunteer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Email cannot be empty")
    @Email(message = "Invalid Email format")
    private String email;

    @NotNull(message = "Bio cannot be empty")
    private String bio;

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;

    @ManyToMany(mappedBy = "volunteers")
    private Set<Project> projects;
}
