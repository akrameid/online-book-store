package com.example.onlinebookstore.config;

import com.example.onlinebookstore.entity.Book;
import com.example.onlinebookstore.repository.BookRepository;
import com.example.onlinebookstore.util.BookUtil;
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

    @Bean
    @Transactional
    CommandLineRunner init(final BookRepository bookRepository) {

        return args -> {

            final Book book1 = this.bookUtil.createBook("book 1", "auth 1", 11, BigDecimal.valueOf(1), true);
            final Book book2 = this.bookUtil.createBook("book 2", "auth 2", 22, BigDecimal.valueOf(2), true);
            bookRepository.save(book1);
            bookRepository.save(book2);
        };
    }
}
