package com.example.subscriptiontservice.model;

import com.example.subscriptiontservice.model.Content;
import com.example.subscriptiontservice.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@DynamicInsert
@Builder
@Entity
@Table(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    private String name;
    private Double price;

    @Builder.Default
    @BatchSize(size = 15)
    @OneToMany(
            mappedBy = "subscription",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER
    )
    private List<Content> contents = new ArrayList<>();

    @Builder.Default
    @BatchSize(size = 15)
    @ManyToMany(mappedBy = "subscriptions", fetch = FetchType.EAGER)
    private Set<User> subscribers = new HashSet<>();

    public void addContent(Content content) {
        contents.add(content);
        content.setSubscription(this);
    }

    public void removeContent(Content content) {
        contents.remove(content);
        content.setSubscription(null);
    }
}
