package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.BookBriefDto;
import com.example.onlinebookstore.dto.BookDto;
import com.example.onlinebookstore.entity.Book;
import com.example.onlinebookstore.exception.BookIdNotExistedException;
import com.example.onlinebookstore.mapper.BookMapper;
import com.example.onlinebookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public List<BookBriefDto> getAllBooks(final String category, final String name) {
        final List<Book> books;
        if (StringUtils.hasText(category) && StringUtils.hasText(name)) {
            books = this.bookRepository.findByNameContainsAndCategory(name, category);
        } else if (StringUtils.hasText(category)) {
            books = this.bookRepository.findByCategory(category);
        } else if (StringUtils.hasText(name)) {
            books = this.bookRepository.findByNameContains(name);
        } else {
            books = this.bookRepository.findAll();
        }
        return this.bookMapper.mapToBriefDto(books);
    }

    public BookDto getBookDetailsById(final Long id) {
        final Book book = this.bookRepository.findById(id).orElseThrow(() -> new BookIdNotExistedException(id));
        return this.bookMapper.mapToDto(book);
    }
}
