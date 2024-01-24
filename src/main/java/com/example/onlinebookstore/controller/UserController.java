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

    @GetMapping("/books")
    public List<BookBriefDto> getAllBooks(@RequestParam(name = "category", required = false) final String category,
                                          @RequestParam(name = "name", required = false) final String name) {
        return this.userService.getAllBooks(category, name);
    }

    @GetMapping("/books/{id}")
    public BookDto getBookDetailsById(@PathVariable("id") final Long id) {
        return this.userService.getBookDetailsById(id);
    }

    @PostMapping("")
    ResponseEntity<String> register(@Valid @RequestBody final NewUserDto newUserDto) {
        return new ResponseEntity<>(this.userService.registerUser(newUserDto), HttpStatus.CREATED);
    }

    @PutMapping("{userId}/books/{bookId}")
    ResponseEntity<String> request(@PathVariable final Long userId, @PathVariable final Long bookId) {
        return new ResponseEntity<>(this.userService.request(userId, bookId), HttpStatus.OK);
    }
}
