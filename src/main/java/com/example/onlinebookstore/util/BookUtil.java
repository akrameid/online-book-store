package com.example.onlinebookstore.util;

import com.example.onlinebookstore.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class BookUtil {

    public Book createBook(final String name, final String authorName, final int stock,
                           final BigDecimal price, final boolean isAvailable, final String category,
                           final Integer numberOfDaysForBorrow, final Integer browsingNumber) {
        return Book.builder()
                .authorName(authorName)
                .stock(stock)
                .name(name)
                .price(price)
                .isAvailable(isAvailable)
                .category(category)
                .numberOfDaysForBorrow(numberOfDaysForBorrow)
                .browsingNumber(browsingNumber)
                .build();
    }
}
