package com.example.onlinebookstore.dto;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BookDto {
    private String name;
    private String authorName;
    private BigDecimal price;
}
