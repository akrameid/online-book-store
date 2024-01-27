package com.example.onlinebookstore.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserBookRequestStatus status;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User referredUser;

    @ManyToOne()
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "totalPrice")
    private Long totalPrice;


    @CreatedDate
    private Timestamp requestedAt;
    private Timestamp updatedAt;
    private Timestamp returnedAt;
}
