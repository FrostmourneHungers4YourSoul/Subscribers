package com.example.userservice.service;

import com.example.userservice.dto.request.ContentRequest;
import com.example.userservice.dto.response.ContentResponse;

import java.util.UUID;

public interface ContentService {
    ContentResponse createContent(ContentRequest content);
    ContentResponse getContentById(UUID uuid);
    ContentResponse updateContentById(UUID uuid, ContentRequest content);
    void removeContentById(UUID uuid);
}
