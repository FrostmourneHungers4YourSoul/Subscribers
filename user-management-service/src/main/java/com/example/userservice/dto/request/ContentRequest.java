package com.example.userservice.dto.request;

import com.example.userservice.model.enums.ContentType;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ContentRequest
        (
                @NotNull
                String name,
                @NotNull
                ContentType type,
                String description,
                @NotNull
                UUID subscriptionUuid
        ) {
}
