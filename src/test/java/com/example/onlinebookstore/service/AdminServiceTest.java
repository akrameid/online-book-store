package com.example.onlinebookstore.service;

import com.example.onlinebookstore.TestUtil;
import com.example.onlinebookstore.dto.BookDto;
import com.example.onlinebookstore.entity.Book;
import com.example.onlinebookstore.exception.BookNameExistedException;
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

import static com.example.onlinebookstore.constant.Constants.ADDED_SUCCESSFULLY;
import static com.example.onlinebookstore.constant.ErrorMessages.BOOK_NAME_EXISTED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

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
}
