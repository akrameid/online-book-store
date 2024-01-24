package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.BookDto;
import com.example.onlinebookstore.entity.Book;
import com.example.onlinebookstore.exception.BookIdNotExistedException;
import com.example.onlinebookstore.exception.BookNameExistedException;
import com.example.onlinebookstore.mapper.BookMapper;
import com.example.onlinebookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.onlinebookstore.constant.Constants.ADDED_SUCCESSFULLY;
import static com.example.onlinebookstore.constant.Constants.UPDATED_SUCCESSFULLY;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public List<BookDto> getAllBooks() {
        final List<Book> books = this.bookRepository.findAll();
        return this.bookMapper.mapToDto(books);
    }

    public String addBook(final BookDto bookDto) {
        validate(bookDto);
        this.bookRepository.save(this.bookMapper.map(bookDto));
        return ADDED_SUCCESSFULLY;
    }

    private void validate(final BookDto bookDto) {
        if (this.bookRepository.existsByName(bookDto.getName())) {
            throw new BookNameExistedException(bookDto.getName());
        }
    }

    public String update(final Long id, final BookDto bookDto) {
        final Optional<Book> optionalBook = this.bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            final Book updatedBook = optionalBook.get();
            updatedBook.setName(bookDto.getName());
            updatedBook.setAuthorName(bookDto.getAuthorName());
            updatedBook.setPrice(bookDto.getPrice());
            updatedBook.setStock(bookDto.getStock());//check null
            updatedBook.setIsAvailable(bookDto.getIsAvailable());
            this.bookRepository.save(updatedBook);
            return UPDATED_SUCCESSFULLY;
        } else {
            throw new BookIdNotExistedException(id);
        }
    }
}
