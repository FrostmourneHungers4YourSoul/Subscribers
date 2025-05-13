package com.example.subscriptiontservice.dto.request;

import com.example.subscriptiontservice.model.enums.ContentType;
import com.example.subscriptiontservice.model.enums.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequest
        (
                @NotNull
                @Size(min = 4, max = 255)
                String username,

                @NotNull
                @Size(min = 4, max = 255)
                String email,

                @NotNull
                @Size(min = 4, max = 255)
                String password,
                ContentType preference,
                Role role
        ) {
}
