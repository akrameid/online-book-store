package com.example.onlinebookstore;

import com.example.onlinebookstore.dto.BookDto;
import com.example.onlinebookstore.dto.NewUserDto;
import com.example.onlinebookstore.entity.Book;
import com.example.onlinebookstore.entity.User;
import com.example.onlinebookstore.entity.UserBookRequest;
import com.example.onlinebookstore.entity.UserBookRequestStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestUtil {
    protected User getTestUser() {
        return User.builder()
                .name("name")
                .password("pw")
                .build();
    }

    protected List<User> getTestUsers() {
        final List<User> users = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            users.add(getTestUser((long) i));
        }
        return users;
    }

    protected User getTestUser(final Long id) {
        return User.builder()
                .name("test user" + id)
                .password("password" + id)
                .id(id)
                .build();
    }

    protected List<Book> getTestBooks() {
        return getTestBooks(4);
    }

    protected List<Book> getTestBooks(final int count) {
        final List<Book> books = new ArrayList<>();
        for (int i = 1; i < count; i++) {
            books.add(getTestBook((long) i));
        }
        return books;
    }

    protected Book getTestBook(final Long bookId) {
        return getTestBook(bookId, "test book " + bookId, "test author " + bookId, Math.toIntExact(bookId), BigDecimal.valueOf(bookId),
                "cat" + bookId, Math.toIntExact(bookId));
    }

    protected BookDto getTestBookDto(final Long id) {
        return getTestBookDto(id, "book " + id, "author " + id, Math.toIntExact(id)
                , BigDecimal.valueOf(id), true, "cat " + id, Math.toIntExact(id));
    }

    protected BookDto getTestBookDto(final Long id, final boolean isAvailable) {
        return getTestBookDto(id, "book " + id, "author " + id, Math.toIntExact(id)
                , BigDecimal.valueOf(id), isAvailable, "cat " + id, Math.toIntExact(id));
    }

    protected BookDto getTestBookDto(final Long id, final String name, final String authorName, final int stock,
                                     final BigDecimal price, final boolean isAvailable, final String category,
                                     final Integer numberOfDaysForBorrow) {
        return BookDto.builder()
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

    protected Book getTestBook(final Long id, final String name, final String authorName, final int stock,
                               final BigDecimal price, final String category,
                               final Integer numberOfDaysForBorrow) {
        return Book.builder()
                .id(id)
                .authorName(authorName)
                .stock(stock)
                .name(name)
                .price(price)
                .isAvailable(true)
                .category(category)
                .numberOfDaysForBorrow(numberOfDaysForBorrow)
                .build();
    }

    protected List<UserBookRequest> getTestUserBookRequests() {
        final List<UserBookRequest> requests = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            requests.add(getTestUserBookRequest((long) i, (long) (i + 1), UserBookRequestStatus.PENDING));
        }
        return requests;
    }

    protected UserBookRequest getTestUserBookRequest(final Long bookId, final Long userId, final UserBookRequestStatus status) {
        return UserBookRequest.builder()
                .book(getTestBook(bookId))
                .referredUser(getTestUser(userId))
                .status(status)
                .requestedAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }

    protected NewUserDto getTestNewUserDto(final String name, final String password) {
        return NewUserDto.builder()
                .name(name)
                .password(password)
                .build();
    }
}
