package com.example.volunet.entity;

import com.example.volunet.entity.enums.ProjectStatus;
import com.example.volunet.entity.enums.SkillType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "project")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Title cannot be empty")
    private String title;

    @NotNull(message = "Description cannot be empty")
    private String description;

    @NotNull(message = "DeadLine cannot be empty")
    private LocalDateTime deadline;

    @Enumerated(EnumType.STRING)
    private SkillType requiredSkill;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @ManyToMany
    @JoinTable(
            name = "project_volunteers",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "volunteer_id")
    )
    private Set<Volunteer> volunteers = new HashSet<>();
}
