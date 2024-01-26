package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.dto.BookDto;
import com.example.onlinebookstore.dto.UserBookRequestDto;
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

    @DeleteMapping("/books/{id}")
    ResponseEntity<String> deleteBook(@PathVariable final Long id) {
        return new ResponseEntity<>(this.adminService.deleteBook(id), HttpStatus.OK);
    }

    @PutMapping("/books/{bookId}")
    ResponseEntity<String> update(@PathVariable final Long bookId, @Valid @RequestBody final BookDto bookDto) {
        return new ResponseEntity<>(this.adminService.update(bookId, bookDto), HttpStatus.OK);
    }


    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        return this.adminService.getAllUsers();
    }

    @GetMapping("/requests")
    public List<UserBookRequestDto> getAllUserBookRequests() {
        return this.adminService.getAllUserBookRequests();
    }

    @PutMapping("/requests/approve/{id}")
    ResponseEntity<String> approve(@PathVariable final Long id) {
        return new ResponseEntity<>(this.adminService.approve(id), HttpStatus.OK);
    }

    @PutMapping("/requests/reject/{id}")
    ResponseEntity<String> reject(@PathVariable final Long id) {
        return new ResponseEntity<>(this.adminService.reject(id), HttpStatus.OK);
    }
}
