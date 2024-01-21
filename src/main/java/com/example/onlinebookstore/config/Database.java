package com.example.onlinebookstore.config;

import com.example.onlinebookstore.entity.Book;
import com.example.onlinebookstore.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Configuration
@Slf4j
public class Database {

//    private static final Logger log = LoggerFactory.getLogger(Database.class);

    @Bean
    @Transactional
    CommandLineRunner init(final BookRepository bookRepository) {

        return args -> {

            final Book book1 = Book.builder().authorName("auth 1").name("book 1").price(BigDecimal.valueOf(1)).build();
            final Book book2 = Book.builder().authorName("auth 2").name("book 2").price(BigDecimal.valueOf(2)).build();
            bookRepository.save(book1);
            bookRepository.save(book2);
        };
    }
}
