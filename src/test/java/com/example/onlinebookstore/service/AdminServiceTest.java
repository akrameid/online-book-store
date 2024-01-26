package com.example.onlinebookstore.service;

import com.example.onlinebookstore.TestUtil;
import com.example.onlinebookstore.entity.Book;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
