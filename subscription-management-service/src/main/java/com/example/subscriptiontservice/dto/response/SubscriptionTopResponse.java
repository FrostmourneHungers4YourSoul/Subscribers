package com.example.subscriptiontservice.dto.response;

import java.util.UUID;

public record SubscriptionTopResponse
        (
                UUID uuid,
                String name,
                int subscribers
        ) {
}
