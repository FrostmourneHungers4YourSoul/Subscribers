package com.example.userservice.service.impl;

import com.example.userservice.dto.Message;
import com.example.userservice.dto.request.SubscriptionRequest;
import com.example.userservice.dto.request.UserRequest;
import com.example.userservice.dto.response.NotificationResponse;
import com.example.userservice.dto.response.SubscriptionResponse;
import com.example.userservice.dto.response.UserResponse;
import com.example.userservice.exception.ResourceNotFoundException;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.model.Notification;
import com.example.userservice.model.Subscription;
import com.example.userservice.model.User;
import com.example.userservice.model.enums.ContentType;
import com.example.userservice.model.enums.Role;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.SubscriptionService;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final SubscriptionService subscriptionService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public UserResponse createUser(UserRequest userRequest) {
        User user = userMapper.toEntity(userRequest);

        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }

        if (user.getPreference() == null) {
            user.setPreference(ContentType.DEFAULT);
        }

        userRepository.save(user);
        log.info("Created new user: {}.", user.getUuid());
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse getUserById(UUID uuid) {
        User user = userRepository.findById(uuid).orElseThrow(() ->
                new ResourceNotFoundException("User: " + uuid + " not found."));
        return userMapper.toResponse(user);
    }

    @Override
    public Set<SubscriptionResponse> getSubscriptions(UUID uuid) {
        Set<Subscription> subscriptions = userRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("User: " + uuid + " not found."))
                .getSubscriptions();
        return userMapper.toSubscriptionResponseSet(subscriptions);
    }

    @Transactional
    @Override
    public UserResponse updateUserById(UUID uuid, UserRequest userRequest) {
        User user = userRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("User " + uuid + " not found."));

        userMapper.updateEntity(user, userRequest);

        User updatedUser = userRepository.save(user);
        log.info("User: {} was updated.", updatedUser.getUuid());

        return userMapper.toResponse(updatedUser);
    }

    @Transactional
    @Override
    public void removeUserById(UUID uuid) {
        if (!userRepository.existsById(uuid))
            throw new ResourceNotFoundException("User: " + uuid + " not found.");

        userRepository.deleteById(uuid);
        log.info("User: {} was removed.", uuid);
    }

    @Transactional
    @Override
    public Message subscribe(UUID uuid, SubscriptionRequest subRequest) {
        User user = userRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("User " + uuid + " not found."));
        return subscriptionService.addSubscriber(subRequest, user);
    }

    @Transactional
    @Override
    public Message unsubscribe(UUID uuid, UUID subUuid) {
        User user = userRepository.findById(uuid)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User: " + uuid + " not found."));
        return subscriptionService.removeSubscriber(subUuid, user);
    }

    @Transactional
    @Override
    public List<NotificationResponse> getNotification(UUID uuid) {
        User user = userRepository.findById(uuid).orElseThrow(() ->
                new ResourceNotFoundException("User: " + uuid + " not found."));
        List<Notification> notifications = user.getNotifications();

        notifications.forEach(notification -> notification.setIsRead(true));

        userRepository.save(user);
        return userMapper.toNotificationResponseList(notifications);
    }
}
