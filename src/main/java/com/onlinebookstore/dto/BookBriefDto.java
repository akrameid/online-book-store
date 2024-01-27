package com.onlinebookstore.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BookBriefDto {
    private Long id;
    private String name;
    private String authorName;
}
