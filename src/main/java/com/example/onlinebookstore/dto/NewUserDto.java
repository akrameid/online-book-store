package com.example.onlinebookstore.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class NewUserDto {
    private String name;
    private String password;
}
