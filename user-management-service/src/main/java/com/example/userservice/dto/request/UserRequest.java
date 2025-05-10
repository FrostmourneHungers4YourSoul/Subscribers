package com.example.userservice.dto.request;

import com.example.userservice.model.enums.ContentType;
import com.example.userservice.model.enums.Role;
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
