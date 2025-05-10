package com.example.userservice.controller;

import com.example.userservice.dto.request.SubscriptionRequest;
import com.example.userservice.dto.response.SubscriptionResponse;
import com.example.userservice.dto.response.SubscriptionTopResponse;
import com.example.userservice.service.SubscriptionService;
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
