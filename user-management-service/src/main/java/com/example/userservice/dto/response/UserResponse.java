package com.example.userservice.dto.response;

import com.example.userservice.model.enums.ContentType;
import com.example.userservice.model.enums.Role;

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
