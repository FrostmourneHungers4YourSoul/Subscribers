package com.example.userservice.mapper;

import com.example.userservice.dto.request.SubscriptionRequest;
import com.example.userservice.dto.response.SubscriptionResponse;
import com.example.userservice.dto.response.SubscriptionTopResponse;
import com.example.userservice.model.Subscription;
import com.example.userservice.model.User;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.Set;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SubscriptionMapper {

    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "subscribers", ignore = true)
    @Mapping(target = "contents", ignore = true)
    Subscription toSubscription(SubscriptionRequest request);

    SubscriptionResponse toSubscriptionResponse(Subscription subscription);

    @Mapping(target = "subscribers", source = "subscription.subscribers")
    SubscriptionTopResponse toSubscriptionTopResponse(Subscription subscription);

    @IterableMapping(elementTargetType = SubscriptionResponse.class)
    List<SubscriptionTopResponse> toSubscriptionResponseSet(List<Subscription> subscriptions);

    default int map(Set<User> value) {
        return value.size();
    }
}
