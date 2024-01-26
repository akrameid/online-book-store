package com.example.onlinebookstore.util;

import com.example.onlinebookstore.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class BookUtil {

    public Book createBook(final String name, final String authorName, final int stock,
                           final BigDecimal price, final String category, final int stockLevel,
                           final Integer numberOfDaysForBorrow) {
        return Book.builder()
                .authorName(authorName)
                .stock(stock)
                .name(name)
                .price(price)
                .category(category)
                .stockLevel(stockLevel)
                .numberOfDaysForBorrow(numberOfDaysForBorrow)
                .build();
    }
}
