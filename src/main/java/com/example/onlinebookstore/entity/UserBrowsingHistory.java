package com.example.onlinebookstore.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_browsing_history_sequence")
    @SequenceGenerator(name = "user_browsing_history_sequence", sequenceName = "user_browsing_history_id_seq", allocationSize = 1)
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
