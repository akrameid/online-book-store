package com.example.onlinebookstore.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.validation.constraints.NotNull;

@Entity(name = "user_browsing_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@EntityListeners(AuditingEntityListener.class)
public class UserBrowsingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User referredUser;

    @NotNull
    @OneToOne()
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "browsingHistory")
    private Long browsingHistory = 0L;
}
