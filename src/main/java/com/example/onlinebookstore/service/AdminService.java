package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.BookDto;
import com.example.onlinebookstore.dto.UserBookRequestDto;
import com.example.onlinebookstore.dto.UserDto;
import com.example.onlinebookstore.entity.Book;
import com.example.onlinebookstore.entity.User;
import com.example.onlinebookstore.exception.BookIdNotExistedException;
import com.example.onlinebookstore.exception.BookNameExistedException;
import com.example.onlinebookstore.mapper.BookMapper;
import com.example.onlinebookstore.mapper.UserBookRequestMapper;
import com.example.onlinebookstore.mapper.UserMapper;
import com.example.onlinebookstore.repository.BookRepository;
import com.example.onlinebookstore.repository.UserBookRequestRepository;
import com.example.onlinebookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.onlinebookstore.constant.Constants.ADDED_SUCCESSFULLY;
import static com.example.onlinebookstore.constant.Constants.UPDATED_SUCCESSFULLY;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserBookRequestRepository userBookRequestRepository;
    private final UserBookRequestMapper userBookRequestMapper;

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

    //TODO: add some validations
    public String update(final Long id, final BookDto bookDto) {
        final Optional<Book> optionalBook = this.bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            final Book updatedBook = optionalBook.get();
            updatedBook.setName(bookDto.getName());
            updatedBook.setAuthorName(bookDto.getAuthorName());
            updatedBook.setPrice(bookDto.getPrice());
            updatedBook.setStock(bookDto.getStock());//TODO: check null
            updatedBook.setIsAvailable(bookDto.getIsAvailable());
            updatedBook.setCategory(bookDto.getCategory());
            this.bookRepository.save(updatedBook);
            return UPDATED_SUCCESSFULLY;
        } else {
            throw new BookIdNotExistedException(id);
        }
    }

    public List<UserDto> getAllUsers() {
        final List<User> users = this.userRepository.findAll();
        return this.userMapper.mapToDto(users);
    }

    public List<UserBookRequestDto> getAllUserBookRequests() {
        final var requests = this.userBookRequestRepository.findAll();
        final List<UserBookRequestDto> allUserBookRequestDtos = new ArrayList<>();
        for (final var request : requests) {
            final UserDto userDto = this.userMapper.mapToDto(request.getReferredUser());
            final BookDto bookDto = this.bookMapper.mapToDto(request.getBook());
            final UserBookRequestDto userBookRequestDto = UserBookRequestDto.builder()
                    .userDto(userDto)
                    .bookDto(bookDto)
                    .status(request.getStatus())
                    .requestedAt(request.getRequestedAt().toLocalDateTime())
                    .build();
            allUserBookRequestDtos.add(userBookRequestDto);
        }
        return allUserBookRequestDtos;
    }
}
