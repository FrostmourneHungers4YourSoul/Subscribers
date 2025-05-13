package com.example.subscriptiontservice.dto.response;

import com.example.subscriptiontservice.model.enums.ContentType;
import com.example.subscriptiontservice.model.enums.Role;

import java.util.UUID;

public record UserResponse
        (
                UUID uuid,
                String username,
                String email,
                ContentType preference,
                Role role
        ) {
}
