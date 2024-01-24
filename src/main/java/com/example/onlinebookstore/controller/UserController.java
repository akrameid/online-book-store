package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.dto.BookBriefDto;
import com.example.onlinebookstore.dto.BookDto;
import com.example.onlinebookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}
