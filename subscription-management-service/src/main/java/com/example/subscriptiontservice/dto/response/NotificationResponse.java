package com.example.subscriptiontservice.dto.response;

import com.example.subscriptiontservice.dto.Message;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationResponse
        (
                UUID target,
                String publisher,
                boolean isRead,

                @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                LocalDateTime dateCreated,
                Message message
        ) {
}
