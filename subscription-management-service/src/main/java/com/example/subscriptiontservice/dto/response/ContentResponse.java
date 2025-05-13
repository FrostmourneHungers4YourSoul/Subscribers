package com.example.subscriptiontservice.dto.response;

import com.example.subscriptiontservice.model.enums.ContentType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record ContentResponse
        (
                UUID uuid,
                String name,
                ContentType type,

                @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                LocalDateTime dateCreated,
                String description,
                UUID subscriptionUuid
        ) {
}
