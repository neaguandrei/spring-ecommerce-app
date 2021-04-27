package com.fmi.user.dao.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "refresh_token")
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserEntity user;

    @Column(length = 38, nullable = false)
    private String token;

    @Column(name = "created", updatable = false, nullable = false)
    @Setter(AccessLevel.NONE)
    @CreationTimestamp
    private LocalDateTime created;

    @Column(name = "revoked")
    private LocalDateTime revoked;

    @Version
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private int version;

    public RefreshTokenEntity(UserEntity user, String token) {
        this.user = user;
        this.token = token;
        this.created = LocalDateTime.now();
    }
}
