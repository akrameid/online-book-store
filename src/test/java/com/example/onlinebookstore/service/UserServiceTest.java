package com.example.onlinebookstore.service;

import com.example.onlinebookstore.TestUtil;
import com.example.onlinebookstore.dto.NewUserDto;
import com.example.onlinebookstore.entity.Book;
import com.example.onlinebookstore.entity.User;
import com.example.onlinebookstore.entity.UserBookRequest;
import com.example.onlinebookstore.entity.UserBookRequestStatus;
import com.example.onlinebookstore.exception.*;
import com.example.onlinebookstore.mapper.BookMapper;
import com.example.onlinebookstore.repository.BookRepository;
import com.example.onlinebookstore.repository.UserBookRequestRepository;
import com.example.onlinebookstore.repository.UserBrowsingHistoryRepository;
import com.example.onlinebookstore.repository.UserRepository;
import com.example.onlinebookstore.util.UserUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static com.example.onlinebookstore.constant.Constants.*;
import static com.example.onlinebookstore.constant.ErrorMessages.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class UserServiceTest extends TestUtil {
    @Mock
    private UserRepository userRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private UserBrowsingHistoryRepository userBrowsingHistoryRepository;
    @Mock
    private BookMapper bookMapper;
    @Mock
    private UserBookRequestRepository userBookRequestRepository;
    @Mock
    private UserUtil userUtil;
    @InjectMocks
    private UserService userService;

    @Test
    public void getAllBooks() {

        final String category = "";
        final Long userId = 1L;
        final String name = "";
        when(this.userRepository.findById(userId)).thenReturn(Optional.ofNullable(getTestUser(userId)));
        final List<Book> books = getTestBooks();
        when(this.bookRepository.findAll()).thenReturn(books);

        when(this.bookMapper.mapToBriefDto(books)).then(
                invocationOnMock -> BookMapper.INSTANCE.mapToBriefDto(books)
        );

        final var result = this.userService.getAllBooks(category, name, userId);
        assertEquals(result.size(), books.size());
    }

    @Test
    public void getAllBooks_filteredByCategory() {

        final String category = "c";
        final Long userId = 1L;
        final String name = "";
        when(this.userRepository.findById(userId)).thenReturn(Optional.ofNullable(getTestUser(userId)));
        final List<Book> books = getTestBooks();
        when(this.bookRepository.findByCategory(category)).thenReturn(books);

        when(this.bookMapper.mapToBriefDto(books)).then(
                invocationOnMock -> BookMapper.INSTANCE.mapToBriefDto(books)
        );

        final var result = this.userService.getAllBooks(category, name, userId);
        assertEquals(result.size(), books.size());
    }

    @Test
    public void getAllBooks_filteredByName() {

        final String category = "";
        final Long userId = 1L;
        final String name = "n";
        when(this.userRepository.findById(userId)).thenReturn(Optional.ofNullable(getTestUser(userId)));
        final List<Book> books = getTestBooks();
        when(this.bookRepository.findByNameContains(name)).thenReturn(books);

        when(this.bookMapper.mapToBriefDto(books)).then(
                invocationOnMock -> BookMapper.INSTANCE.mapToBriefDto(books)
        );

        final var result = this.userService.getAllBooks(category, name, userId);
        assertEquals(result.size(), books.size());
    }

    @Test
    public void getAllBooks_filteredByNameAndCategory() {

        final String category = "c";
        final Long userId = 1L;
        final String name = "n";
        when(this.userRepository.findById(userId)).thenReturn(Optional.ofNullable(getTestUser(userId)));
        final List<Book> books = getTestBooks();
        when(this.bookRepository.findByNameContainsAndCategory(name, category)).thenReturn(books);

        when(this.bookMapper.mapToBriefDto(books)).then(
                invocationOnMock -> BookMapper.INSTANCE.mapToBriefDto(books)
        );

        final var result = this.userService.getAllBooks(category, name, userId);
        assertEquals(result.size(), books.size());
    }

    @Test
    public void getAllBooks_checkHistory() {

        final String category = "";
        final Long userId = 1L;
        final String name = "";
        when(this.userRepository.findById(userId)).thenReturn(Optional.ofNullable(getTestUser(userId)));
        final List<Book> books = getTestBooks();
        when(this.bookRepository.findAll()).thenReturn(books);

        when(this.bookMapper.mapToBriefDto(books)).then(
                invocationOnMock -> BookMapper.INSTANCE.mapToBriefDto(books)
        );

        final var result = this.userService.getAllBooks(category, name, userId);
        assertEquals(result.size(), books.size());
        verify(this.userBrowsingHistoryRepository, times(books.size())).save(any());
    }

    @Test
    public void getBookDetailsById() {
        final Long bookId = 1L;
        final Book testBook = getTestBook(bookId);
        when(this.bookRepository.findById(bookId)).thenReturn(Optional.ofNullable(testBook));

        when(this.bookMapper.mapToDto(testBook)).then(
                invocationOnMock -> BookMapper.INSTANCE.mapToDto(testBook)
        );

        final var result = this.userService.getBookDetailsById(bookId);
        assert testBook != null;
        assertEquals(result.getId(), testBook.getId());
    }

    @Test
    public void getBookDetailsById_bookNotFound() {
        final Long bookId = 1L;
        when(this.bookRepository.findById(bookId)).thenReturn(Optional.empty());
        final BookIdNotExistedException exception = assertThrows(BookIdNotExistedException.class,
                () -> this.userService.getBookDetailsById(bookId));
        assertEquals(String.format(BOOK_ID_NOT_EXISTED, bookId), exception.getMessage());
    }

    @Test
    public void requestBorrow() {
        final Long userId = 1L;
        final Long bookId = 1L;
        final Book testBook = getTestBook(bookId);
        testBook.setIsAvailable(true);
        when(this.bookRepository.findById(bookId)).thenReturn(Optional.of(testBook));
        when(this.userBookRequestRepository.findByBook_IdAndReferredUser_IdAndStatus(bookId, userId, UserBookRequestStatus.PENDING)).thenReturn(Optional.empty());
        final User testUser = getTestUser(userId);
        when(this.userRepository.findById(userId)).thenReturn(Optional.ofNullable(testUser));

        final ArgumentCaptor<UserBookRequest> captor = ArgumentCaptor.forClass(UserBookRequest.class);
        final var result = this.userService.requestBorrow(userId, bookId);
        verify(this.userBookRequestRepository).save(any());
        verify(this.userBookRequestRepository).save(captor.capture());
        final UserBookRequest capturedUserBookRequest = captor.getValue();
        assertEquals(testUser, capturedUserBookRequest.getReferredUser());
        assertEquals(testBook, capturedUserBookRequest.getBook());
        assertEquals(UserBookRequestStatus.PENDING, capturedUserBookRequest.getStatus());
        assertEquals(String.format(USER_REQUEST_RECEIVED, testBook.getName(), testBook.getNumberOfDaysForBorrow()), result);
    }

    @Test
    public void requestBorrow_bookIdNotFound() {
        final Long userId = 1L;
        final Long bookId = 1L;
        final Book testBook = getTestBook(bookId);
        testBook.setIsAvailable(true);
        when(this.bookRepository.findById(bookId)).thenReturn(Optional.empty());
        final BookIdNotExistedException exception = assertThrows(BookIdNotExistedException.class,
                () -> this.userService.requestBorrow(userId, bookId));
        assertEquals(String.format(BOOK_ID_NOT_EXISTED, bookId), exception.getMessage());
    }

    @Test
    public void requestBorrow_requestAlreadyInProgress() {
        final Long userId = 1L;
        final Long bookId = 1L;
        final Book testBook = getTestBook(bookId);
        testBook.setIsAvailable(true);
        when(this.bookRepository.findById(bookId)).thenReturn(Optional.of(testBook));
        when(this.userBookRequestRepository.findByBook_IdAndReferredUser_IdAndStatus(bookId, userId, UserBookRequestStatus.PENDING)).thenReturn(Optional.of(getTestUserBookRequest(bookId, userId, UserBookRequestStatus.PENDING)));

        final BookRequestInProgressException exception = assertThrows(BookRequestInProgressException.class,
                () -> this.userService.requestBorrow(userId, bookId));
        assertEquals(String.format(BOOK_REQUEST_IN_PROGRESS, testBook.getName()), exception.getMessage());
    }

    @Test
    public void requestBorrow_userIdNotFound() {
        final Long userId = 1L;
        final Long bookId = 1L;
        final Book testBook = getTestBook(bookId);
        testBook.setIsAvailable(true);
        when(this.bookRepository.findById(bookId)).thenReturn(Optional.of(testBook));
        when(this.userBookRequestRepository.findByBook_IdAndReferredUser_IdAndStatus(bookId, userId, UserBookRequestStatus.PENDING)).thenReturn(Optional.empty());
        final User testUser = getTestUser(userId);
        when(this.userRepository.findById(userId)).thenReturn(Optional.empty());

        final UserIdNotExistedException exception = assertThrows(UserIdNotExistedException.class,
                () -> this.userService.requestBorrow(userId, bookId));
        assertEquals(String.format(USER_ID_NOT_EXISTED, userId), exception.getMessage());
    }

    @Test
    public void requestBorrow_bookNotAvailable() {
        final Long userId = 1L;
        final Long bookId = 1L;
        final Book testBook = getTestBook(bookId);
        testBook.setIsAvailable(false);
        when(this.bookRepository.findById(bookId)).thenReturn(Optional.of(testBook));
        when(this.userBookRequestRepository.findByBook_IdAndReferredUser_IdAndStatus(bookId, userId, UserBookRequestStatus.PENDING)).thenReturn(Optional.empty());
        final User testUser = getTestUser(userId);
        when(this.userRepository.findById(userId)).thenReturn(Optional.ofNullable(testUser));


        final BookNotAvailableException exception = assertThrows(BookNotAvailableException.class,
                () -> this.userService.requestBorrow(userId, bookId));
        assertEquals(String.format(BOOK_NOT_AVAILABLE, testBook.getName()), exception.getMessage());
    }

    @Test
    public void registerUser() {
        final String name = "n1";
        final String password = "p1";
        final NewUserDto testNewUserDto = getTestNewUserDto(name, password);
        when(this.userRepository.findByNameAndPassword(name, password)).thenReturn(Optional.empty());
        when(this.userUtil.createUser(name, password)).thenCallRealMethod();
        final var result = this.userService.registerUser(testNewUserDto);
        assertEquals(USER_ADDED_SUCCESSFULLY, result);
        verify(this.userRepository, times(1)).save(any());
    }

    @Test
    public void registerUser_alreadyRegistered() {
        final String name = "n2";
        final String password = "p2";
        final NewUserDto testNewUserDto = getTestNewUserDto(name, password);
        when(this.userRepository.findByNameAndPassword(name, password)).thenReturn(Optional.of(getTestUser()));
        final UserAlreadyRegisteredException exception = assertThrows(UserAlreadyRegisteredException.class,
                () -> this.userService.registerUser(testNewUserDto));
        assertEquals(String.format(USER_ALREADY_REGISTERED, name), exception.getMessage());
    }

    @Test
    public void returnBook() {
        final Long userId = 2L;
        final Long bookId = 2L;
        final UserBookRequest testUserBookRequest = getTestUserBookRequest(bookId, userId, UserBookRequestStatus.APPROVED);
        testUserBookRequest.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        when(this.userBookRequestRepository.findByBook_IdAndReferredUser_Id(bookId, userId)).thenReturn(Optional.of(testUserBookRequest));
        final var result = this.userService.returnBook(userId, bookId);
        verify(this.userBookRequestRepository, times(1)).save(any());
        final long differenceInMinutes = ChronoUnit.MINUTES.between(testUserBookRequest.getReturnedAt().toLocalDateTime(), LocalDateTime.now());
        assertEquals(0, differenceInMinutes);
        assertEquals(true, testUserBookRequest.getBook().getIsAvailable());
        verify(this.bookRepository, times(1)).save(any());
        assertEquals(USER_BOOK_RETURNED, result);
    }

    @Test
    public void returnBook_noBookBorrowed() {
        final Long userId = 2L;
        final Long bookId = 2L;
        when(this.userBookRequestRepository.findByBook_IdAndReferredUser_Id(bookId, userId)).thenReturn(Optional.empty());
        final BookRequestNotCreatedException exception = assertThrows(BookRequestNotCreatedException.class,
                () -> this.userService.returnBook(userId, bookId));
        assertEquals(String.format(BOOK_REQUEST_WITH_BOOK_USER_NOT_CREATED, bookId, userId), exception.getMessage());
    }

    @Test
    public void returnBook_requestNotApproved() {
        final Long userId = 2L;
        final Long bookId = 2L;
        final UserBookRequest testUserBookRequest = getTestUserBookRequest(bookId, userId, UserBookRequestStatus.PENDING);
        testUserBookRequest.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        when(this.userBookRequestRepository.findByBook_IdAndReferredUser_Id(bookId, userId)).thenReturn(Optional.of(testUserBookRequest));
        final BookRequestNotApprovedException exception = assertThrows(BookRequestNotApprovedException.class,
                () -> this.userService.returnBook(userId, bookId));
        assertEquals(String.format(BOOK_REQUEST_NOT_APPROVED, testUserBookRequest.getBook().getName()), exception.getMessage());
    }

    @Test
    public void returnBook_withExtraFees() {
        final Long userId = 2L;
        final Long bookId = 2L;
        final UserBookRequest testUserBookRequest = getTestUserBookRequest(bookId, userId, UserBookRequestStatus.APPROVED);
        testUserBookRequest.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now().minusDays(10)));
        testUserBookRequest.getBook().setNumberOfDaysForBorrow(2);
        when(this.userBookRequestRepository.findByBook_IdAndReferredUser_Id(bookId, userId)).thenReturn(Optional.of(testUserBookRequest));
        final var result = this.userService.returnBook(userId, bookId);
        verify(this.userBookRequestRepository, times(1)).save(any());
        final long differenceInMinutes = ChronoUnit.MINUTES.between(testUserBookRequest.getReturnedAt().toLocalDateTime(), LocalDateTime.now());
        assertEquals(0, differenceInMinutes);
        assertEquals(true, testUserBookRequest.getBook().getIsAvailable());
        verify(this.bookRepository, times(1)).save(any());
        assertEquals(USER_BOOK_RETURNED_LATE, result);
    }

    @Test
    public void returnBook_alreadyReturned() {
        final Long userId = 2L;
        final Long bookId = 2L;
        final UserBookRequest testUserBookRequest = getTestUserBookRequest(bookId, userId, UserBookRequestStatus.APPROVED);
        testUserBookRequest.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        testUserBookRequest.setReturnedAt(Timestamp.valueOf(LocalDateTime.now()));
        when(this.userBookRequestRepository.findByBook_IdAndReferredUser_Id(bookId, userId)).thenReturn(Optional.of(testUserBookRequest));
        final BookReturnedException exception = assertThrows(BookReturnedException.class,
                () -> this.userService.returnBook(userId, bookId));
        assertEquals(String.format(BOOK_RETURNED, testUserBookRequest.getBook().getName()), exception.getMessage());
    }

    @Test
    public void getSuggestedBooks() {
        final Long userId = 3L;
        final List<Book> testBooks = getTestBooks(3);
        when(this.bookRepository.getSuggestedBooks(userId)).thenReturn(testBooks);
        when(this.bookMapper.mapToBriefDto(testBooks)).then(
                invocationOnMock -> BookMapper.INSTANCE.mapToBriefDto(testBooks)
        );
        final var result = this.userService.getSuggestedBooks(userId);
        assertEquals(testBooks.size(), result.size());
    }
}
