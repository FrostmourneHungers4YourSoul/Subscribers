package com.example.userservice.service.impl;

import com.example.userservice.dto.request.ContentRequest;
import com.example.userservice.dto.response.ContentResponse;
import com.example.userservice.exception.ResourceNotFoundException;
import com.example.userservice.mapper.ContentMapper;
import com.example.userservice.model.Content;
import com.example.userservice.model.enums.ContentType;
import com.example.userservice.repository.ContentRepository;
import com.example.userservice.service.ContentService;
import com.example.userservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {

    private final ContentRepository contentRepository;
    private final ContentMapper contentMapper;
    private final SubscriptionService subscriptionService;

    @Transactional
    @Override
    public ContentResponse createContent(ContentRequest request) {
        Content content = contentMapper.toEntity(request);

        if (content.getType() == null)
            content.setType(ContentType.DEFAULT);

        contentRepository.saveAndFlush(content);

        subscriptionService.addContentToSubscription(request.subscriptionUuid(), content);
        return contentMapper.toResponse(content);
    }

    @Override
    public ContentResponse getContentById(UUID uuid) {
        Content content = contentRepository.findById(uuid).orElseThrow(() ->
                new ResourceNotFoundException("Content " + uuid + " not found."));
        return contentMapper.toResponse(content);
    }

    @Transactional
    @Override
    public ContentResponse updateContentById(UUID uuid, ContentRequest request) {
        Content content = contentRepository.findById(uuid).orElseThrow(() ->
                new ResourceNotFoundException("Content " + uuid + " not found."));

        Content updatedContent = contentMapper.updateEntity(content, request);
        Content responseContent = contentRepository.save(updatedContent);
        return contentMapper.toResponse(responseContent);
    }

    @Transactional
    @Override
    public void removeContentById(UUID uuid) {
        Content content = contentRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Content " + uuid + " not found."));
        contentRepository.delete(content);
    }
}
