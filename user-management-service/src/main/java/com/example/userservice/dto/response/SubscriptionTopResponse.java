package com.example.userservice.dto.response;

import java.util.UUID;

public record SubscriptionTopResponse
        (
                UUID uuid,
                String name,
                int subscribers
        ) {
}
