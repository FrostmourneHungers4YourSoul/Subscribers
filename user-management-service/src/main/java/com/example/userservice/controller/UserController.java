package com.example.userservice.controller;

import com.example.userservice.dto.Message;
import com.example.userservice.dto.request.SubscriptionRequest;
import com.example.userservice.dto.request.UserRequest;
import com.example.userservice.dto.response.NotificationResponse;
import com.example.userservice.dto.response.SubscriptionResponse;
import com.example.userservice.dto.response.UserResponse;
import com.example.userservice.model.enums.Role;
import com.example.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping()
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest userRequest) {
        UserResponse userResponse = userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UserResponse> getUser(@PathVariable UUID uuid) {
        UserResponse userResponse = userService.getUserById(uuid);
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<UserResponse> update(@PathVariable UUID uuid,
                                               @RequestBody @Valid UserRequest userRequest) {
        UserResponse userResponse = userService.updateUserById(uuid, userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        userService.removeUserById(uuid);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{uuid}/subscriptions")
    public ResponseEntity<Message> addSubscription(
            @PathVariable UUID uuid,
            @RequestBody @Valid SubscriptionRequest subRequest) {
        Message message = userService.subscribe(uuid, subRequest);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{uuid}/subscriptions/{sub_uuid}")
    public ResponseEntity<Message> removeSubscription(@PathVariable UUID uuid,
                                                      @PathVariable UUID sub_uuid) {
        Message message = userService.unsubscribe(uuid, sub_uuid);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{uuid}/subscriptions")
    public ResponseEntity<Set<SubscriptionResponse>> getSubscriptions(@PathVariable UUID uuid) {
        Set<SubscriptionResponse> subscriptionResponseSet = userService.getSubscriptions(uuid);
        return ResponseEntity.ok(subscriptionResponseSet);
    }

    @GetMapping("/{uuid}/notifications")
    public ResponseEntity<List<NotificationResponse>> getNotifications(@PathVariable UUID uuid) {
        return ResponseEntity.ok(userService.getNotification(uuid));
    }

    @GetMapping("/roles")
    public List<String> getRoles() {
        return Arrays.stream(Role.values())
                .map(Enum::name)
                .toList();
    }
}
