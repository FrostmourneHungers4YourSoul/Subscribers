package com.example.userservice.service.impl;

import com.example.userservice.dto.Message;
import com.example.userservice.dto.request.SubscriptionRequest;
import com.example.userservice.dto.response.SubscriptionResponse;
import com.example.userservice.dto.response.SubscriptionTopResponse;
import com.example.userservice.event.SubscriptionEvent;
import com.example.userservice.exception.ResourceNotFoundException;
import com.example.userservice.mapper.SubscriptionMapper;
import com.example.userservice.model.Content;
import com.example.userservice.model.QSubscription;
import com.example.userservice.model.Subscription;
import com.example.userservice.model.User;
import com.example.userservice.model.enums.ContentType;
import com.example.userservice.repository.SubscriptionRepository;
import com.example.userservice.service.SubscriptionService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final JPAQueryFactory queryFactory;
    private final SubscriptionRepository subRepository;
    private final SubscriptionMapper subMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    @Override
    public SubscriptionResponse create(SubscriptionRequest request) {
        Subscription subscription = subMapper.toSubscription(request);
        subscription = subRepository.save(subscription);
        return subMapper.toSubscriptionResponse(subscription);
    }

    @Override
    public Subscription getSubscriptionByUuid(UUID uuid) {
        return subRepository.findById(uuid).orElseThrow(() ->
                new ResourceNotFoundException("Subscription: " + uuid + " not found."));
    }

    @Transactional(readOnly = true)
    @Override
    public List<SubscriptionTopResponse> getTop3Service() {
        QSubscription subscription = QSubscription.subscription;

        List<Subscription> subscriptions = queryFactory
                .selectFrom(subscription)
                .where(subscription.subscribers.size().gt(0))
                .orderBy(subscription.subscribers.size().desc())
                .limit(3)
                .fetch();

        log.info("Retrieved {} subscriptions for top 3", subscriptions.size());
        return subMapper.toSubscriptionResponseSet(subscriptions);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public Subscription getSubscriptionByName(SubscriptionRequest subRequest) {
        Optional<Subscription> subscription = subRepository.findByName(subRequest.name());
        if (subscription.isEmpty()) {
            log.info("Subscription by name: {} not found.", subRequest.name());
            Subscription sub = Subscription.builder()
                    .name(subRequest.name())
                    .price(subRequest.price())
                    .build();
            sub = subRepository.save(sub);
            log.info("Created subscription: {}", sub.getUuid());
            return sub;
        }
        return subscription.get();
    }

    @Transactional
    public Message addSubscriber(SubscriptionRequest subRequest, User user) {
        Subscription subscription = getSubscriptionByName(subRequest);

        if (user.getSubscriptions().contains(subscription)) {
            throw new IllegalArgumentException("User is already subscribed.");
        }
        user.getSubscriptions().add(subscription);

        Message message = new Message("You are successfully subscribed to " + subscription.getName());
        eventPublisher.publishEvent(new SubscriptionEvent(this, user, subscription, message));
        return message;
    }

    @Transactional
    public Message removeSubscriber(UUID uuid, User user) {
        Subscription subscription = getSubscriptionByUuid(uuid);
        user.getSubscriptions().remove(subscription);

        Message message = new Message("You are unsubscribed from " + subscription.getName());

        eventPublisher.publishEvent(new SubscriptionEvent(this, user, subscription, message));
        return message;
    }


    @Transactional
    public void addContentToSubscription(UUID subscriptionUuid, Content content) {
        Subscription subscription = subRepository.findById(subscriptionUuid)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription " + subscriptionUuid + " not found"));

        subscription.addContent(content);
        subscription = subRepository.save(subscription);


        for (User user : subscription.getSubscribers()) {
            if (user.getPreference() == content.getType()
                || user.getPreference() == ContentType.DEFAULT) {
                contentNotification(user, subscription, content);
            }
        }
    }

    //Notify user about new content
    private void contentNotification(User user, Subscription subscription, Content content) {
        Message message = new Message("New content in " + subscription.getName()
                                      + ": " + content.getName()
                                      + "\n  " + content.getDescription());
        eventPublisher.publishEvent(new SubscriptionEvent(this, user, subscription, message));
    }
}
