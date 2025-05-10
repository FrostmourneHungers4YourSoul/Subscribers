package com.example.userservice.model;

import com.example.userservice.model.enums.ContentType;
import com.example.userservice.model.enums.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
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
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    private String username;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private ContentType preference = ContentType.DEFAULT;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @BatchSize(size = 15)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_subscriptions",
            joinColumns = @JoinColumn(name = "user_uuid"),
            inverseJoinColumns = @JoinColumn(name = "subscription_uuid")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Subscription> subscriptions = new HashSet<>();

    @BatchSize(size = 15)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_notifications",
            joinColumns = @JoinColumn(name = "target")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Notification> notifications = new ArrayList<>();

}
