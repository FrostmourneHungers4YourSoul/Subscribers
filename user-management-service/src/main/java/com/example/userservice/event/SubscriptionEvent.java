package com.example.userservice.event;

import com.example.userservice.dto.Message;
import com.example.userservice.model.Subscription;
import com.example.userservice.model.User;
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
