package com.example.onlinebookstore;

import com.example.onlinebookstore.entity.Book;
import com.example.onlinebookstore.entity.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TestUtil {
    protected User getTestUser(final String name, final String password) {
        return User.builder()
                .name(name)
                .password(password)
                .build();
    }

    protected User getTestUser(final Long id) {
        return User.builder()
                .name("test user")
                .password("password")
                .id(id)
                .build();
    }

    protected List<Book> getTestBooks() {
        final List<Book> books = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            books.add(getTestBook((long) i));
        }
        return books;
    }

    protected Book getTestBook(final Long bookId) {
        return getTestBook(bookId, "test book " + bookId, "test author " + bookId, 1, BigDecimal.valueOf(1),
                true, "cat", 1);
    }

    protected Book getTestBook(final Long id, final String name, final String authorName, final int stock,
                               final BigDecimal price, final boolean isAvailable, final String category,
                               final Integer numberOfDaysForBorrow) {
        return Book.builder()
                .id(id)
                .authorName(authorName)
                .stock(stock)
                .name(name)
                .price(price)
                .isAvailable(isAvailable)
                .category(category)
                .numberOfDaysForBorrow(numberOfDaysForBorrow)
                .build();
    }
}