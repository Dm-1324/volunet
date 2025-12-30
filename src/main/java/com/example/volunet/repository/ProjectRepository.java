package com.example.volunet.repository;

import com.example.volunet.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query(value = "SELECT * FROM project p WHERE" +
            "(:skill IS NULL OR p.required_skill = :skill) AND" +
            "(:status is NULL or p.status = :status)",
            countQuery = "SELECT COUNT(*) FROM project p WHERE" +
                    "(:skill IS NULL OR p.required_skill = :skill) AND" +
                    "(:status is NULL or p.status = :status)",
            nativeQuery = true
    )
    Page<Project> filterProjects(@Param("skill") String skill, @Param("status") String status, Pageable pageable);
}