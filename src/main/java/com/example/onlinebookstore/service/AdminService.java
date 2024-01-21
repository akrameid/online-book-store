package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.BookDto;
import com.example.onlinebookstore.entity.Book;
import com.example.onlinebookstore.mapper.BookMapper;
import com.example.onlinebookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public List<BookDto> getAllBooks() {
        final List<Book> books = this.bookRepository.findAll();
        return this.bookMapper.mapBook(books);
    }
}
