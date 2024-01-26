package com.example.onlinebookstore.service;

import com.example.onlinebookstore.TestUtil;
import com.example.onlinebookstore.dto.BookDto;
import com.example.onlinebookstore.entity.Book;
import com.example.onlinebookstore.entity.User;
import com.example.onlinebookstore.entity.UserBookRequest;
import com.example.onlinebookstore.exception.BookIdNotExistedException;
import com.example.onlinebookstore.exception.BookNameExistedException;
import com.example.onlinebookstore.exception.BookNameExistedInOtherBookException;
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

import static com.example.onlinebookstore.constant.Constants.ADDED_SUCCESSFULLY;
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
}
