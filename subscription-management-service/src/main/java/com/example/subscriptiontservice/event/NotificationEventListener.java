package com.example.subscriptiontservice.event;

import com.example.subscriptiontservice.model.Notification;
import com.example.subscriptiontservice.model.Subscription;
import com.example.subscriptiontservice.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventListener {


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void handleSubscriberEvent(SubscriptionEvent event) {
        log.info("Received Subscription Event for user UUID: {}", event.getUser().getUuid());
        try {
            User user = event.getUser();
            Subscription subscription = event.getSubscription();
            String message = event.getMessage();

            log.debug("Creating notification for user: {}, subscription: {}, message: {}",
                    user.getUuid(), subscription.getName(), message);

            Notification notification = Notification.builder()
                    .target(user.getUuid())
                    .publisher(subscription.getName())
                    .message(message)
                    .isRead(false)
                    .dateCreated(LocalDateTime.now()
                            .plusNanos(ThreadLocalRandom.current().nextInt(1000)))
                    .build();

            user.getNotifications().add(notification);
        } catch (Exception e) {
            log.error("Failed to process Subscription Event for user UUID: {}. Error: {}",
                    event.getUser().getUuid(), e.getMessage(), e);
        }
    }
}
