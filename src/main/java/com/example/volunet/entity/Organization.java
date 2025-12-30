package com.example.volunet.entity;

import com.example.volunet.entity.enums.VerifiedStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "organization", uniqueConstraints = {
        @UniqueConstraint
                (name = "unique_organization_data",
                        columnNames = {"name", "email"})
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Email cannot be empty")
    private String email;

    @NotNull(message = "Description cannot be empty")
    private String description;

    @NotNull(message = "Location cannot be empty")
    private String location;

    @Enumerated(EnumType.STRING)
    private VerifiedStatus verifiedStatus;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    private List<Project> projects = new ArrayList<>();
}
