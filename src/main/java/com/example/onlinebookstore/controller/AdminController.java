package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.dto.BookDto;
import com.example.onlinebookstore.dto.UserDto;
import com.example.onlinebookstore.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/books")
    public List<BookDto> getAllBooks() {
        return this.adminService.getAllBooks();
    }

    @PostMapping("/books")
    ResponseEntity<String> addBook(@Valid @RequestBody final BookDto bookDto) {
        return new ResponseEntity<>(this.adminService.addBook(bookDto), HttpStatus.CREATED);
    }

    @PutMapping("/books/{id}")
    ResponseEntity<String> update(@PathVariable final Long id, @Valid @RequestBody final BookDto bookDto) {
        return new ResponseEntity<>(this.adminService.update(id, bookDto), HttpStatus.OK);
    }


    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        return this.adminService.getAllUsers();
    }

}
