package com.example.userservice.event;

import com.example.userservice.model.Notification;
import com.example.userservice.model.Subscription;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
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

    private final UserRepository userRepository;


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
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Failed to process Subscription Event for user UUID: {}. Error: {}",
                    event.getUser().getUuid(), e.getMessage(), e);
        }
    }
}
