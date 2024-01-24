package com.example.onlinebookstore.util;

import com.example.onlinebookstore.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtil {

    public User createUser(final String name, final String password) {
        return User.builder()
                .name(name)
                .password(password)
                .build();
    }
}
