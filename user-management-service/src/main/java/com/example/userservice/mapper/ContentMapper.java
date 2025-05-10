package com.example.userservice.mapper;

import com.example.userservice.dto.request.ContentRequest;
import com.example.userservice.dto.response.ContentResponse;
import com.example.userservice.model.Content;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ContentMapper {

    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(source = "subscriptionUuid", target = "subscription.uuid")
    Content toEntity(ContentRequest request);


    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "subscription.uuid", source = "request.subscriptionUuid")
    @Mapping(target = "description", source = "request.description")
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "type", source = "request.type")
    Content updateEntity(Content content, ContentRequest request);

    @Mapping(source = "subscription.uuid", target = "subscriptionUuid")
    ContentResponse toResponse(Content content);
}
