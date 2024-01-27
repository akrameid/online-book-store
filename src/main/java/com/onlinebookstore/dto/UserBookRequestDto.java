package com.onlinebookstore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.onlinebookstore.entity.UserBookRequestStatus;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserBookRequestDto {
    private Long id;
    private UserDto userDto;
    private BookDto bookDto;
    private LocalDateTime requestedAt;
    private LocalDateTime updatedAt;
    private LocalDateTime returnedAt;
    private UserBookRequestStatus status;
}
