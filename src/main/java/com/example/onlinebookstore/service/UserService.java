package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.BookBriefDto;
import com.example.onlinebookstore.dto.BookDto;
import com.example.onlinebookstore.dto.NewUserDto;
import com.example.onlinebookstore.entity.Book;
import com.example.onlinebookstore.exception.BookIdNotExistedException;
import com.example.onlinebookstore.exception.UserWithNameExistedException;
import com.example.onlinebookstore.mapper.BookMapper;
import com.example.onlinebookstore.mapper.UserMapper;
import com.example.onlinebookstore.repository.BookRepository;
import com.example.onlinebookstore.repository.UserRepository;
import com.example.onlinebookstore.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.onlinebookstore.constant.Constants.USER_ADDED_SUCCESSFULLY;

@Service
@RequiredArgsConstructor
public class UserService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final UserRepository userRepository;
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

    public String borrow(final Long userId, final Long bookId) {
        return "";
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
}
