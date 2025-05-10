package com.example.userservice.dto.response;

import java.util.UUID;

public record SubscriptionResponse
        (
                UUID uuid,
                String name,
                Double price
        ) {

}
