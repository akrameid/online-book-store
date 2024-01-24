package com.example.onlinebookstore.dto;

import com.example.onlinebookstore.entity.UserBookRequestStatus;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserBookRequestDto {
    private UserDto userDto;
    private BookDto bookDto;
    private LocalDateTime requestedAt;
    private UserBookRequestStatus status;
}
