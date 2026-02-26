package com.example.volunet.service;

import com.example.volunet.entity.Organization;
import com.example.volunet.entity.Volunteer;
import com.example.volunet.repository.OrganizationRepository;
import com.example.volunet.repository.VolunteerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final VolunteerRepository volunteerRepository;
    private final OrganizationRepository organizationRepository;

    public CustomUserDetailsService(VolunteerRepository volunteerRepository, OrganizationRepository organizationRepository) {
        this.volunteerRepository = volunteerRepository;
        this.organizationRepository = organizationRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Volunteer> volunteer = volunteerRepository.findByEmail(email);
        if (volunteer.isPresent()) {
            return User.builder()
                    .username(volunteer.get().getName())
                    .password(volunteer.get().getPassword())
                    .roles("VOLUNTEER")
                    .build();
        }
        Optional<Organization> organization = organizationRepository.findByEmail(email);
        if (organization.isPresent()) {
            return User.builder()
                    .username(organization.get().getEmail())
                    .password(organization.get().getPassword())
                    .roles("ORGANIZATION")
                    .build();
        }
        throw new UsernameNotFoundException("No user found with email: " + email);
    }
}
