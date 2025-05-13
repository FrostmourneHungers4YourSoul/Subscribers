package com.example.subscriptiontservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Notification {

    @Column(name = "target", insertable = false, updatable = false)
    private UUID target;

    private String publisher;

    private String message;

    @Column(name = "read")
    private Boolean isRead;

    @CreationTimestamp
    @Column(name = "date_created")
    private LocalDateTime dateCreated;

}
