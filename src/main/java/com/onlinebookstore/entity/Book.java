package com.onlinebookstore.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity(name = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@EntityListeners(AuditingEntityListener.class)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String authorName;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Integer inStock;
    @Column(nullable = false, columnDefinition = "int default 0")
    @Builder.Default()
    private Integer borrowedCopiesCount = 0;
    @Column(nullable = false, columnDefinition = "int default 1")
    @Builder.Default()
    private Integer stockLevel = 1;
    @CreatedDate
    private Timestamp createdAt;
    @LastModifiedDate
    private Timestamp updatedAt;
    @NotNull
    private String category;
    private Integer numberOfDaysForBorrow;
}
