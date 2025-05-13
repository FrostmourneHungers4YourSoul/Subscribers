package com.example.subscriptiontservice.service;

import com.example.subscriptiontservice.dto.Message;
import com.example.subscriptiontservice.dto.request.SubscriptionRequest;
import com.example.subscriptiontservice.dto.response.SubscriptionResponse;
import com.example.subscriptiontservice.dto.response.SubscriptionTopResponse;
import com.example.subscriptiontservice.model.Content;
import com.example.subscriptiontservice.model.Subscription;
import com.example.subscriptiontservice.model.User;

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
