package com.example.onlinebookstore.service;

import com.example.onlinebookstore.TestUtil;
import com.example.onlinebookstore.entity.Book;
import com.example.onlinebookstore.mapper.BookMapper;
import com.example.onlinebookstore.repository.BookRepository;
import com.example.onlinebookstore.repository.UserBrowsingHistoryRepository;
import com.example.onlinebookstore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
                invocationOnMock -> BookMapper.INSTANCE.mapToDto(books)
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
                invocationOnMock -> BookMapper.INSTANCE.mapToDto(books)
        );

        final var result = this.userService.getAllBooks(category, name, userId);
        assertEquals(result.size(), books.size());
        verify(this.userBrowsingHistoryRepository, times(books.size())).save(any());
    }
}