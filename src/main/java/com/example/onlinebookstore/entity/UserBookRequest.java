package com.example.onlinebookstore.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity(name = "user_book_requests")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@EntityListeners(AuditingEntityListener.class)
public class UserBookRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    private String name;
    @NotNull
    private String password;
    @CreatedDate
    private Timestamp requestedAt;
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserBookRequestStatus status;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User referredUser;

    @NotNull
    @OneToOne()
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "totalPrice")
    private Long totalPrice;
}
