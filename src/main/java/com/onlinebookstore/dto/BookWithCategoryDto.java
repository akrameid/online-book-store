package com.onlinebookstore.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BookWithCategoryDto {
    private String category;
    private List<BookBriefDto> books;
}
