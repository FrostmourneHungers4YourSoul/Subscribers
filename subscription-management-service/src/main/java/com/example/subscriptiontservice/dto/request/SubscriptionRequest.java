package com.example.subscriptiontservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SubscriptionRequest
        (
                @NotNull
                @Size(max = 255)
                String name,
                Double price
        ) {

}
