package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.BookBriefDto;
import com.example.onlinebookstore.dto.BookDto;
import com.example.onlinebookstore.dto.NewUserDto;
import com.example.onlinebookstore.entity.Book;
import com.example.onlinebookstore.entity.User;
import com.example.onlinebookstore.entity.UserBookRequest;
import com.example.onlinebookstore.entity.UserBookRequestStatus;
import com.example.onlinebookstore.exception.*;
import com.example.onlinebookstore.mapper.BookMapper;
import com.example.onlinebookstore.mapper.UserMapper;
import com.example.onlinebookstore.repository.BookRepository;
import com.example.onlinebookstore.repository.UserBookRequestRepository;
import com.example.onlinebookstore.repository.UserRepository;
import com.example.onlinebookstore.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.example.onlinebookstore.constant.Constants.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final UserRepository userRepository;
    private final UserBookRequestRepository userBookRequestRepository;
    private final UserMapper userMapper;
    private final UserUtil userUtil;

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

    public String requestBorrow(final Long userId, final Long bookId) {
        final Book book = this.bookRepository.findById(bookId).orElseThrow();//TODO: handle exception
        final var request = this.userBookRequestRepository.findByBook_IdAndReferredUser_Id(bookId, userId);
        if (request.isPresent()) {
            if (request.get().getStatus().equals(UserBookRequestStatus.PENDING)) {
                throw new BookRequestInProgressException(book.getName());
            }
        }
        final User user = this.userRepository.findById(userId).orElseThrow();//TODO: handle exception
        if (!book.getIsAvailable()) {
            throw new BookNotAvailableException(book.getName());
        }
        final UserBookRequest userBookRequest = UserBookRequest.builder()
                .referredUser(user)
                .book(book)
                .status(UserBookRequestStatus.PENDING)
                .build();
        this.userBookRequestRepository.save(userBookRequest);
        return String.format(USER_REQUEST_RECEIVED, book.getName(), book.getNumberOfDaysForBorrow());
    }

    public String registerUser(final NewUserDto newUserDto) {
        final var existingUser = this.userRepository.findByNameAndPassword(newUserDto.getName(), newUserDto.getPassword());
        if (existingUser.isPresent()) {
            throw new UserWithNameExistedException(newUserDto.getName());
        } else {
            this.userRepository.save(this.userUtil.createUser(newUserDto.getName(), newUserDto.getPassword()));
            return USER_ADDED_SUCCESSFULLY;
        }
    }

    public String returnBook(final Long userId, final Long bookId) {
        final var request = this.userBookRequestRepository.findByBook_IdAndReferredUser_Id(bookId, userId).orElseThrow();//TODO: add exception
        if (!request.getStatus().equals(UserBookRequestStatus.APPROVED)) {
            throw new BookRequestNotFoundException(request.getBook().getName());
        }
        if (request.getReturnedAt() != null) {
            throw new BookReturnedException(request.getBook().getName());
        }
        request.setReturnedAt(Timestamp.valueOf(LocalDateTime.now()));
        this.userBookRequestRepository.save(request);
        final Book book = request.getBook();
        book.setIsAvailable(true);
        this.bookRepository.save(book);
        if (ChronoUnit.DAYS.between(request.getApprovedAt().toLocalDateTime(), LocalDateTime.now()) > request.getBook().getNumberOfDaysForBorrow()) {
            return USER_BOOK_RETURNED_LATE;
        } else {
            return USER_BOOK_RETURNED;
        }
    }
}
