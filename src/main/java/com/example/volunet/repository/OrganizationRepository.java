package com.example.volunet.repository;

import com.example.volunet.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    @Query(value = "SELECT EXISTS(SELECT 1 FROM organization WHERE BINARY name = :name OR BINARY email = :email)", nativeQuery = true)
    Long existsByNameOrEmailCaseSensitive(@Param("name") String name, @Param("email") String email);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM organization WHERE BINARY name = :name)", nativeQuery = true)
    Long existsByNameCaseSensitive(@Param("name") String name);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM organization WHERE BINARY email = :email)", nativeQuery = true)
    Long existsByEmailCaseSensitive(@Param("email") String email);

}