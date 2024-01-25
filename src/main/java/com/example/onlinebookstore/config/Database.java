package com.example.onlinebookstore.config;

import com.example.onlinebookstore.entity.Book;
import com.example.onlinebookstore.entity.User;
import com.example.onlinebookstore.repository.BookRepository;
import com.example.onlinebookstore.repository.UserRepository;
import com.example.onlinebookstore.util.BookUtil;
import com.example.onlinebookstore.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class Database {

    private final BookUtil bookUtil;
    private final UserUtil userUtil;

    @Bean
    @Transactional
    CommandLineRunner init(final BookRepository bookRepository, final UserRepository userRepository) {

        return args -> {
            final Book book1 = this.bookUtil.createBook("book 1", "auth 1", 11,
                    BigDecimal.valueOf(1), true, "Category 1", 1, 0);
            final Book book2 = this.bookUtil.createBook("book 2", "auth 2", 22,
                    BigDecimal.valueOf(2), true, "Category 2", 2, 0);
            bookRepository.save(book1);
            bookRepository.save(book2);
            final User user1 = this.userUtil.createUser("User 1", "pw1");
            userRepository.save(user1);
            final User user2 = this.userUtil.createUser("User 2", "pw2");
            userRepository.save(user2);
        };
    }
}
