package com.example.onlinebookstore.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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
    private BigDecimal price;
    private Integer stock;
    @JsonIgnore
    private LocalDateTime createdAt;
    @JsonIgnore
    private LocalDateTime updatedAt;
    private Boolean isAvailable;
    private String category;
}
