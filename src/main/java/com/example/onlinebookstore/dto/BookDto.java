package com.example.onlinebookstore.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BookDto {
    @JsonIgnore
    private Long id;
    private String name;
    private String authorName;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Integer inStock;
    @JsonIgnore
    private LocalDateTime createdAt;
    @JsonIgnore
    private LocalDateTime updatedAt;
    @NotNull
    private String category;
    @NotNull
    private Integer numberOfDaysForBorrow;
    private Integer stockLevel;
    @Builder.Default()
    private Integer borrowedCopiesCount = 0;
}
