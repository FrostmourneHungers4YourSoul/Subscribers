package com.example.subscriptiontservice.event;

import com.example.subscriptiontservice.dto.Message;
import com.example.subscriptiontservice.model.Subscription;
import com.example.subscriptiontservice.model.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SubscriptionEvent extends ApplicationEvent {

    private final User user;
    private final Subscription subscription;
    private final String message;

    public SubscriptionEvent(Object source, User user, Subscription subscription, Message message) {
        super(source);
        this.user = user;
        this.subscription = subscription;
        this.message = message.message();
    }
}
