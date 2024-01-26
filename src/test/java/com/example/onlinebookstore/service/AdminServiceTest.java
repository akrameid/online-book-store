package com.example.onlinebookstore.service;

import com.example.onlinebookstore.TestUtil;
import com.example.onlinebookstore.dto.BookDto;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.example.onlinebookstore.constant.Constants.*;
import static com.example.onlinebookstore.constant.ErrorMessages.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class AdminServiceTest extends TestUtil {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserBookRequestRepository userBookRequestRepository;
    @InjectMocks
    private AdminService adminService;

    @Test
    public void getAllBooks() {
        final List<Book> testBooks = getTestBooks();
        when(this.bookRepository.findAll()).thenReturn(testBooks);
        when(this.bookMapper.mapToDto(testBooks)).then(
                invocationOnMock -> BookMapper.INSTANCE.mapToDto(testBooks));
        final var result = this.adminService.getAllBooks();
        assertEquals(testBooks.size(), result.size());
    }

    @Test
    public void addBook() {
        final BookDto bookDto = getTestBookDto(1L, true);
        when(this.bookRepository.existsByName(bookDto.getName())).thenReturn(false);
        when(this.bookMapper.map(bookDto)).then(
                invocationOnMock -> BookMapper.INSTANCE.map(bookDto));
        final var result = this.adminService.addBook(bookDto);
        assertEquals(ADDED_SUCCESSFULLY, result);
    }

    @Test
    public void addBook_bookExisted() {
        final BookDto bookDto = getTestBookDto(2L, false);
        when(this.bookRepository.existsByName(bookDto.getName())).thenReturn(true);
        final BookNameExistedException exception = assertThrows(BookNameExistedException.class,
                () -> this.adminService.addBook(bookDto));
        assertEquals(String.format(BOOK_NAME_EXISTED, bookDto.getName()), exception.getMessage());
    }

    @Test
    public void update() {
        final Long bookId = 3L;
        final BookDto bookDto = getTestBookDto(bookId);
        final Book testBook = getTestBook(bookId);
        when(this.bookRepository.findById(bookId)).thenReturn(Optional.of(testBook));
        when(this.bookRepository.existsByNameAndIdNotIn(bookDto.getName(), List.of(bookId))).thenReturn(false);
        this.adminService.update(bookId, bookDto);
        verify(this.bookRepository, times(1)).save(testBook);
    }

    @Test
    public void update_newNameAlreadyExisted() {
        final Long bookId = 3L;
        final BookDto bookDto = getTestBookDto(bookId);
        final Book testBook = getTestBook(bookId);
        when(this.bookRepository.findById(bookId)).thenReturn(Optional.of(testBook));
        when(this.bookRepository.existsByNameAndIdNotIn(bookDto.getName(), List.of(bookId))).thenReturn(true);
        final BookNameExistedInOtherBookException exception = assertThrows(BookNameExistedInOtherBookException.class,
                () -> this.adminService.update(bookId, bookDto));
        assertEquals(String.format(BOOK_NAME_EXISTED_IN_ANOTHER_BOOK, testBook.getName()), exception.getMessage());
    }

    @Test
    public void update_bookNotFound() {
        final Long bookId = 3L;
        final BookDto bookDto = getTestBookDto(bookId);
        when(this.bookRepository.findById(bookId)).thenReturn(Optional.empty());
        final BookIdNotExistedException exception = assertThrows(BookIdNotExistedException.class,
                () -> this.adminService.update(bookId, bookDto));
        assertEquals(String.format(BOOK_ID_NOT_EXISTED, bookId), exception.getMessage());
    }

    @Test
    public void getAllUsers() {
        final List<User> testUsers = getTestUsers();
        when(this.userRepository.findAll()).thenReturn(testUsers);
        when(this.userMapper.mapToDto(testUsers)).then(
                invocationOnMock -> UserMapper.INSTANCE.mapToDto(testUsers));
        final var result = this.adminService.getAllUsers();
        assertEquals(testUsers.size(), result.size());
    }

    @Test
    public void getAllUserBookRequests() {
        final List<UserBookRequest> testUserBookRequests = getTestUserBookRequests();
        when(this.userBookRequestRepository.findAll()).thenReturn(testUserBookRequests);
        when(this.userMapper.mapToDto(any(User.class))).then(
                invocationOnMock ->
                {
                    final User user = invocationOnMock.getArgument(0);
                    return UserMapper.INSTANCE.mapToDto(user);
                });
        when(this.bookMapper.mapToDto(any(Book.class))).then(
                invocationOnMock ->
                {
                    final Book book = invocationOnMock.getArgument(0);
                    return BookMapper.INSTANCE.mapToDto(book);
                });
        final var result = this.adminService.getAllUserBookRequests();
        assertEquals(testUserBookRequests.size(), result.size());
    }

    @Test
    public void approve() {
        final Long id = 3L;
        final UserBookRequest testUserBookRequest = getTestUserBookRequest(id, UserBookRequestStatus.PENDING);
        when(this.userBookRequestRepository.findById(id)).thenReturn(Optional.of(testUserBookRequest));
        final var result = this.adminService.approve(id);
        verify(this.userBookRequestRepository, times(1)).save(testUserBookRequest);
        verify(this.bookRepository, times(1)).save(testUserBookRequest.getBook());
        assertEquals(String.format(USER_REQUEST_APPROVED, testUserBookRequest.getBook().getName()), result);
        assertEquals(false, testUserBookRequest.getBook().getIsAvailable());
        assertEquals(UserBookRequestStatus.APPROVED, testUserBookRequest.getStatus());
    }

    @Test
    public void approve_requestNotCreated() {
        final Long id = 4L;
        final UserBookRequest testUserBookRequest = getTestUserBookRequest(id, UserBookRequestStatus.PENDING);
        when(this.userBookRequestRepository.findById(id)).thenReturn(Optional.empty());
        final BookRequestNotCreatedException exception = assertThrows(BookRequestNotCreatedException.class,
                () -> this.adminService.approve(id));
        assertEquals(String.format(BOOK_REQUEST_NOT_CREATED, id), exception.getMessage());
    }

    @Test
    public void approve_alreadyApproved() {
        final Long id = 2L;
        final UserBookRequest testUserBookRequest = getTestUserBookRequest(id, UserBookRequestStatus.APPROVED);
        when(this.userBookRequestRepository.findById(id)).thenReturn(Optional.of(testUserBookRequest));
        final BookRequestAlreadyApprovedException exception = assertThrows(BookRequestAlreadyApprovedException.class,
                () -> this.adminService.approve(id));
        assertEquals(String.format(BOOK_REQUEST_ALREADY_APPROVED, id), exception.getMessage());
    }

    @Test
    public void reject() {
        final Long id = 3L;
        final UserBookRequest testUserBookRequest = getTestUserBookRequest(id, UserBookRequestStatus.PENDING);
        when(this.userBookRequestRepository.findById(id)).thenReturn(Optional.of(testUserBookRequest));
        final var result = this.adminService.reject(id);
        verify(this.userBookRequestRepository, times(1)).save(testUserBookRequest);
        assertEquals(String.format(USER_REQUEST_REJECTED, testUserBookRequest.getBook().getName()), result);
        assertEquals(UserBookRequestStatus.REJECTED, testUserBookRequest.getStatus());
    }

    @Test
    public void reject_requestNotCreated() {
        final Long id = 3L;
        final UserBookRequest testUserBookRequest = getTestUserBookRequest(id, UserBookRequestStatus.PENDING);
        when(this.userBookRequestRepository.findById(id)).thenReturn(Optional.empty());
        final BookRequestNotCreatedException exception = assertThrows(BookRequestNotCreatedException.class,
                () -> this.adminService.reject(id));
        assertEquals(String.format(BOOK_REQUEST_NOT_CREATED, id), exception.getMessage());
    }

    @Test
    public void reject_alreadyRejected() {
        final Long id = 8L;
        final UserBookRequest testUserBookRequest = getTestUserBookRequest(id, UserBookRequestStatus.REJECTED);
        when(this.userBookRequestRepository.findById(id)).thenReturn(Optional.of(testUserBookRequest));
        final BookRequestAlreadyRejectedException exception = assertThrows(BookRequestAlreadyRejectedException.class,
                () -> this.adminService.reject(id));
        assertEquals(String.format(BOOK_REQUEST_ALREADY_REJECTED, id), exception.getMessage());
    }
}
