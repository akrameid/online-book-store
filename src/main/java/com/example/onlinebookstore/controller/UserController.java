package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.dto.BookBriefDto;
import com.example.onlinebookstore.dto.BookDto;
import com.example.onlinebookstore.dto.NewUserDto;
import com.example.onlinebookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("{id}/books")
    public List<BookBriefDto> getAllBooks(@RequestParam(name = "category", required = false) final String category,
                                          @RequestParam(name = "name", required = false) final String name,
                                          @PathVariable("id") final Long userId) {
        return this.userService.getAllBooks(category, name, userId);
    }

    @GetMapping("{id}/books/suggest")
    public List<BookBriefDto> suggestBooks(@PathVariable("id") final Long userId) {
        return this.userService.getSuggestedBooks(userId);
    }

    @GetMapping("/books/{bookId}")
    public BookDto getBookDetailsById(@PathVariable("bookId") final Long bookId) {
        return this.userService.getBookDetailsById(bookId);
    }

    @PostMapping("")
    ResponseEntity<String> register(@Valid @RequestBody final NewUserDto newUserDto) {
        return new ResponseEntity<>(this.userService.registerUser(newUserDto), HttpStatus.CREATED);
    }

    @PutMapping("{userId}/books/{bookId}/borrow")
    ResponseEntity<String> request(@PathVariable final Long userId, @PathVariable final Long bookId) {
        return new ResponseEntity<>(this.userService.requestBorrow(userId, bookId), HttpStatus.OK);
    }

    @PutMapping("{userId}/books/{bookId}/return")
    ResponseEntity<String> returnBook(@PathVariable final Long userId, @PathVariable final Long bookId) {
        return new ResponseEntity<>(this.userService.returnBook(userId, bookId), HttpStatus.OK);
    }
}
