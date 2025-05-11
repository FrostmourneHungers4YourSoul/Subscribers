package com.example.userservice.controller;

import com.example.userservice.dto.request.ContentRequest;
import com.example.userservice.dto.response.ContentResponse;
import com.example.userservice.model.enums.ContentType;
import com.example.userservice.service.ContentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/contents")
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;

    @PostMapping
    public ResponseEntity<ContentResponse> createContent(@RequestBody @Valid ContentRequest content) {
        ContentResponse createdContent = contentService.createContent(content);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContent);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ContentResponse> getContentById(@PathVariable UUID uuid) {
        ContentResponse content = contentService.getContentById(uuid);
        return ResponseEntity.ok(content);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ContentResponse> updateContentById(@PathVariable UUID uuid,
            @RequestBody @Valid ContentRequest content) {
        ContentResponse updatedContent = contentService.updateContentById(uuid, content);
        return ResponseEntity.ok(updatedContent);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> removeContentById(@PathVariable UUID uuid) {
        contentService.removeContentById(uuid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/content-types")
    public List<String> getContentTypes() {
        return Arrays.stream(ContentType.values())
                .map(Enum::name)
                .toList();
    }
}
