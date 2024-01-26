package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.BookDto;
import com.example.onlinebookstore.dto.UserBookRequestDto;
import com.example.onlinebookstore.dto.UserDto;
import com.example.onlinebookstore.entity.Book;
import com.example.onlinebookstore.entity.User;
import com.example.onlinebookstore.entity.UserBookRequestStatus;
import com.example.onlinebookstore.exception.*;
import com.example.onlinebookstore.mapper.BookMapper;
import com.example.onlinebookstore.mapper.UserMapper;
import com.example.onlinebookstore.repository.BookRepository;
import com.example.onlinebookstore.repository.UserBookRequestRepository;
import com.example.onlinebookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.onlinebookstore.constant.Constants.*;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserBookRequestRepository userBookRequestRepository;

    @Value("${max.number.of.book.copies}")
    private Integer maxNumberOfBookCopies;

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
        if (bookDto.getStock() > this.maxNumberOfBookCopies) {
            throw new BookCopiesExceededException(bookDto.getName(), bookDto.getStock(), this.maxNumberOfBookCopies);
        }
    }

    public String update(final Long bookId, final BookDto bookDto) {
        final Book book = this.bookRepository.findById(bookId).orElseThrow(() -> new BookIdNotExistedException(bookId));
        final String newBookName = bookDto.getName();
        if (this.bookRepository.existsByNameAndIdNotIn(newBookName, List.of(bookId))) {
            throw new BookNameExistedInOtherBookException(book.getName());
        }
        book.setName(newBookName);
        book.setAuthorName(bookDto.getAuthorName());
        book.setPrice(bookDto.getPrice());
        book.setStock(bookDto.getStock());
        book.setCategory(bookDto.getCategory());
        this.bookRepository.save(book);
        return UPDATED_SUCCESSFULLY;
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
            final LocalDateTime updatedAt = request.getUpdatedAt() == null ? null : request.getUpdatedAt().toLocalDateTime();
            final LocalDateTime returnedAt = request.getReturnedAt() == null ? null : request.getReturnedAt().toLocalDateTime();
            final UserBookRequestDto userBookRequestDto = UserBookRequestDto.builder()
                    .userDto(userDto)
                    .bookDto(bookDto)
                    .status(request.getStatus())
                    .requestedAt(request.getRequestedAt().toLocalDateTime())
                    .id(request.getId())
                    .updatedAt(updatedAt)
                    .returnedAt(returnedAt)
                    .build();
            allUserBookRequestDtos.add(userBookRequestDto);
        }
        return allUserBookRequestDtos;
    }

    public String approve(final Long id) {
        final var request = this.userBookRequestRepository.findById(id).
                orElseThrow(() -> new BookRequestNotCreatedException(id));
        if (request.getStatus().equals(UserBookRequestStatus.APPROVED)) {
            throw new BookRequestAlreadyApprovedException(id);
        }
        if (request.getBook().getStock() < 1) {
            throw new BookNotAvailableException(request.getBook().getName());
        }
        request.setStatus(UserBookRequestStatus.APPROVED);
        request.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        this.userBookRequestRepository.save(request);
        final Book book = request.getBook();
        book.setStock(book.getStock() - 1);
        this.bookRepository.save(book);
        return String.format(USER_REQUEST_APPROVED, book.getName());
    }

    public String reject(final Long id) {
        final var request =
                this.userBookRequestRepository.findById(id).
                        orElseThrow(() -> new BookRequestNotCreatedException(id));
        if (request.getStatus().equals(UserBookRequestStatus.REJECTED)) {
            throw new BookRequestAlreadyRejectedException(id);
        }
        request.setStatus(UserBookRequestStatus.REJECTED);
        request.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        this.userBookRequestRepository.save(request);
        return String.format(USER_REQUEST_REJECTED, request.getBook().getName());
    }
}
