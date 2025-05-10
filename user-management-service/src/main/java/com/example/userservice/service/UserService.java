package com.example.userservice.service;

import com.example.userservice.dto.Message;
import com.example.userservice.dto.request.SubscriptionRequest;
import com.example.userservice.dto.request.UserRequest;
import com.example.userservice.dto.response.NotificationResponse;
import com.example.userservice.dto.response.SubscriptionResponse;
import com.example.userservice.dto.response.UserResponse;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);
    UserResponse getUserById(UUID uuid);
    UserResponse updateUserById(UUID uuid, UserRequest userRequest);
    void removeUserById(UUID uuid);

    Set<SubscriptionResponse> getSubscriptions(UUID uuid);
    Message subscribe(UUID uuid, SubscriptionRequest subRequest);
    Message unsubscribe(UUID uuid, UUID subUuid);

    List<NotificationResponse> getNotification(UUID uuid);
}
