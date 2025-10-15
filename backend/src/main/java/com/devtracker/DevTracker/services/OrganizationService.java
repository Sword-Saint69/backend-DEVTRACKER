package com.devtracker.DevTracker.services;

import com.devtracker.DevTracker.dto.organization.OrganizationCreateDTO;
import com.devtracker.DevTracker.dto.organization.OrganizationJoinDTO;
import com.devtracker.DevTracker.model.Organization;
import com.devtracker.DevTracker.model.User;
import com.devtracker.DevTracker.repository.OrganizationRepository;
import com.devtracker.DevTracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;

    public Organization createOrganization(OrganizationCreateDTO dto) {
        User owner = userRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        Organization org = new Organization();
        org.setName(dto.getName());
        org.setDescription(dto.getDescription());
        org.setOwner(owner);
        org.generatePasscode(); // Generate passcode when creating org

        return organizationRepository.save(org);
    }

    public String joinOrganization(OrganizationJoinDTO dto) {
        // Validate input
        if (dto.getOrgId() <= 0) {
            throw new RuntimeException("Invalid organization ID");
        }
        
        if (dto.getPasscode() == null || dto.getPasscode().trim().isEmpty()) {
            throw new RuntimeException("Passcode is required");
        }
        
        if (dto.getUserId() <= 0) {
            throw new RuntimeException("Invalid user ID");
        }

        Organization org = organizationRepository.findById(dto.getOrgId())
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        // Check if organization has a passcode
        if (org.getJoinPasscode() == null || org.getJoinPasscode().isEmpty()) {
            throw new RuntimeException("Organization passcode not set");
        }

        if (!org.getJoinPasscode().equals(dto.getPasscode())) {
            throw new RuntimeException("Invalid passcode");
        }

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setOrganization(org);
        userRepository.save(user);

        return "User " + user.getUserName() + " joined organization " + org.getName();
    }

    public String getPasscode(int orgId, int requesterId) {
        Organization org = organizationRepository.findById(orgId)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        if (org.getOwner().getUserId() != requesterId) {
            throw new RuntimeException("Only owner can view passcode");
        }

        return org.getJoinPasscode();
    }


    public String regeneratePasscode(int orgId, int requesterId) {
        Organization org = organizationRepository.findById(orgId)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        if (org.getOwner().getUserId() != requesterId) {
            throw new RuntimeException("Only owner can regenerate passcode");
        }

        org.setJoinPasscode(UUID.randomUUID().toString().substring(0, 8));
        String newCode= org.getJoinPasscode();
        organizationRepository.save(org);
        return newCode;
    }
}