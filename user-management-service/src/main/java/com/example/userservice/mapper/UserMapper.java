package com.example.userservice.mapper;

import com.example.userservice.dto.Message;
import com.example.userservice.dto.request.UserRequest;
import com.example.userservice.dto.response.NotificationResponse;
import com.example.userservice.dto.response.SubscriptionResponse;
import com.example.userservice.dto.response.UserResponse;
import com.example.userservice.model.Notification;
import com.example.userservice.model.Subscription;
import com.example.userservice.model.User;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.Set;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "subscriptions", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    User toEntity(UserRequest userRequest);

    UserResponse toResponse(User user);

    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "subscriptions", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    void updateEntity(@MappingTarget User user, UserRequest userRequest);

    SubscriptionResponse toSubscriptionResponse(Subscription subscription);

    NotificationResponse toNotificationResponse(Notification notification);

    @IterableMapping(elementTargetType = SubscriptionResponse.class)
    Set<SubscriptionResponse> toSubscriptionResponseSet(Set<Subscription> subscriptions);

    @IterableMapping(elementTargetType = NotificationResponse.class)
    List<NotificationResponse> toNotificationResponseList(List<Notification> notifications);


    default Message map(String value) {
        return new Message(value);
    }
}
