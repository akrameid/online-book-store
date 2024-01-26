package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.BookBriefDto;
import com.example.onlinebookstore.dto.BookDto;
import com.example.onlinebookstore.dto.NewUserDto;
import com.example.onlinebookstore.entity.*;
import com.example.onlinebookstore.exception.*;
import com.example.onlinebookstore.mapper.BookMapper;
import com.example.onlinebookstore.mapper.UserMapper;
import com.example.onlinebookstore.repository.BookRepository;
import com.example.onlinebookstore.repository.UserBookRequestRepository;
import com.example.onlinebookstore.repository.UserBrowsingHistoryRepository;
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
    private final UserBrowsingHistoryRepository userBrowsingHistoryRepository;

    public List<BookBriefDto> getAllBooks(final String category, final String name, final Long userId) {
        final var user = this.userRepository.findById(userId).orElseThrow();//TODO: add exception
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
        adjustBrowsingNumber(books, user);
        return this.bookMapper.mapToBriefDto(books);
    }

    private void adjustBrowsingNumber(final List<Book> books, final User user) {
        for (final Book book : books) {
            final UserBrowsingHistory userBrowsingHistory = getUserBrowsingHistory(user, book);
            userBrowsingHistory.setBrowsingHistory(userBrowsingHistory.getBrowsingHistory() + 1);
            this.userBrowsingHistoryRepository.save(userBrowsingHistory);
        }
        this.bookRepository.saveAll(books);
    }

    private UserBrowsingHistory getUserBrowsingHistory(final User user, final Book book) {
        final UserBrowsingHistory userBrowsingHistory;
        final var userBrowsingHistoryOptional = this.userBrowsingHistoryRepository.findByReferredUser_IdAndBook_Id(user.getId(), book.getId());
        userBrowsingHistory = userBrowsingHistoryOptional.orElseGet(() -> UserBrowsingHistory.builder()
                .book(book)
                .referredUser(user)
                .browsingHistory(0L)
                .build());
        return userBrowsingHistory;
    }

    public BookDto getBookDetailsById(final Long bookId) {
        final Book book = this.bookRepository.findById(bookId).orElseThrow(() -> new BookIdNotExistedException(bookId));
        return this.bookMapper.mapToDto(book);
    }

    public String requestBorrow(final Long userId, final Long bookId) {
        final Book book = this.bookRepository.findById(bookId).orElseThrow(() -> new BookIdNotExistedException(bookId));
        final var request = this.userBookRequestRepository.findByBook_IdAndReferredUser_IdAndStatus(bookId, userId, UserBookRequestStatus.PENDING);
        if (request.isPresent()) {
            throw new BookRequestInProgressException(book.getName());
        }
        final User user = this.userRepository.findById(userId).orElseThrow(() -> new UserIdNotExistedException(userId)); //TODO: handle exception
        if (book.getInStock() < 1) {
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
            throw new UserAlreadyRegisteredException(newUserDto.getName());
        } else {
            this.userRepository.save(this.userUtil.createUser(newUserDto.getName(), newUserDto.getPassword()));
            return USER_ADDED_SUCCESSFULLY;
        }
    }

    public String returnBook(final Long userId, final Long bookId) {
        final var request =
                this.userBookRequestRepository.findByBook_IdAndReferredUser_Id(bookId, userId).
                        orElseThrow(() -> new BookRequestNotCreatedException(bookId, userId));
        if (!request.getStatus().equals(UserBookRequestStatus.APPROVED)) {
            throw new BookRequestNotApprovedException(request.getBook().getName());
        }
        if (request.getReturnedAt() != null) {
            throw new BookReturnedException(request.getBook().getName());
        }
        request.setReturnedAt(Timestamp.valueOf(LocalDateTime.now()));
        this.userBookRequestRepository.save(request);
        final Book book = request.getBook();
        book.setInStock(book.getInStock() + 1);
        if (book.getBorrowedCopiesCount() == 0) {
            throw new BookBorrowCopiesNotValidException(bookId);
        }
        book.setBorrowedCopiesCount(book.getBorrowedCopiesCount() - 1);
        this.bookRepository.save(book);
        if (ChronoUnit.DAYS.between(request.getUpdatedAt().toLocalDateTime(), LocalDateTime.now()) > request.getBook().getNumberOfDaysForBorrow()) {
            return USER_BOOK_RETURNED_LATE;
        } else {
            return USER_BOOK_RETURNED;
        }
    }

    public List<BookBriefDto> getSuggestedBooks(final Long userId) {
        final List<Book> books = this.bookRepository.getSuggestedBooks(userId);
        return this.bookMapper.mapToBriefDto(books);
    }
}
