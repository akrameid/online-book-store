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

import java.util.List;
import java.util.Optional;

import static com.example.onlinebookstore.constant.Constants.USER_ADDED_SUCCESSFULLY;
import static com.example.onlinebookstore.constant.Constants.USER_REQUEST_RECEIVED;
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
        when(this.userBookRequestRepository.findByBook_IdAndReferredUser_Id(bookId, userId)).thenReturn(Optional.empty());
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
        when(this.userBookRequestRepository.findByBook_IdAndReferredUser_Id(bookId, userId)).thenReturn(Optional.of(getTestUserBookRequest(bookId, userId, UserBookRequestStatus.PENDING)));

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
        when(this.userBookRequestRepository.findByBook_IdAndReferredUser_Id(bookId, userId)).thenReturn(Optional.empty());
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
        when(this.userBookRequestRepository.findByBook_IdAndReferredUser_Id(bookId, userId)).thenReturn(Optional.empty());
        final User testUser = getTestUser(userId);
        when(this.userRepository.findById(userId)).thenReturn(Optional.ofNullable(testUser));


        final BookNotAvailableException exception = assertThrows(BookNotAvailableException.class,
                () -> this.userService.requestBorrow(userId, bookId));
        assertEquals(String.format(BOOK_NOT_AVAILABLE, testBook.getName()), exception.getMessage());
    }

    @Test
    public void registerUser() {
        final String name = "n";
        final String password = "p";
        final NewUserDto testNewUserDto = getTestNewUserDto(name, password);
        when(this.userRepository.findByNameAndPassword(name, password)).thenReturn(Optional.empty());
        when(this.userUtil.createUser(name, password)).thenCallRealMethod();
        final var result = this.userService.registerUser(testNewUserDto);
        assertEquals(USER_ADDED_SUCCESSFULLY, result);
        verify(this.userRepository, times(1)).save(any());
    }

    @Test
    public void registerUser_alreadyRegistered() {
        final String name = "n";
        final String password = "p";
        final NewUserDto testNewUserDto = getTestNewUserDto(name, password);
        when(this.userRepository.findByNameAndPassword(name, password)).thenReturn(Optional.of(getTestUser(name, password)));
        final UserAlreadyRegisteredException exception = assertThrows(UserAlreadyRegisteredException.class,
                () -> this.userService.registerUser(testNewUserDto));
        assertEquals(String.format(USER_ALREADY_REGISTERED, name), exception.getMessage());
    }

}
