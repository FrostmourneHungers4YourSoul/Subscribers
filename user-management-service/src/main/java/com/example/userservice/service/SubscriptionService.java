package com.example.userservice.service;

import com.example.userservice.dto.Message;
import com.example.userservice.dto.request.SubscriptionRequest;
import com.example.userservice.dto.response.SubscriptionResponse;
import com.example.userservice.dto.response.SubscriptionTopResponse;
import com.example.userservice.model.Content;
import com.example.userservice.model.Subscription;
import com.example.userservice.model.User;

import java.util.List;
import java.util.UUID;

public interface SubscriptionService {

    SubscriptionResponse create(SubscriptionRequest request);

    Subscription getSubscriptionByUuid(UUID uuid);

    List<SubscriptionTopResponse> getTop3Service();

    Subscription getSubscriptionByName(SubscriptionRequest subRequest);

    void addContentToSubscription(UUID uuid, Content content);

    Message addSubscriber(SubscriptionRequest subRequest, User user);

    Message removeSubscriber(UUID uuid, User user);
}
