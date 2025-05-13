package com.example.subscriptiontservice.controller;


import com.example.subscriptiontservice.dto.request.SubscriptionRequest;
import com.example.subscriptiontservice.dto.response.SubscriptionResponse;
import com.example.subscriptiontservice.dto.response.SubscriptionTopResponse;
import com.example.subscriptiontservice.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subService;

    @GetMapping("/top")
    public ResponseEntity<List<SubscriptionTopResponse>> getTopSubscriptions() {
        List<SubscriptionTopResponse> subResponseSet = subService.getTop3Service();
        return ResponseEntity.ok(subResponseSet);
    }

    @PostMapping
    public ResponseEntity<SubscriptionResponse> createSubscription(
            @RequestBody @Valid SubscriptionRequest subRequest) {
        return ResponseEntity.ok(subService.create(subRequest));
    }
}
