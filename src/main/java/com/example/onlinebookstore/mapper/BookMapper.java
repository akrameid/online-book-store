package com.example.onlinebookstore.mapper;

import com.example.onlinebookstore.dto.BookDto;
import com.example.onlinebookstore.entity.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    List<BookDto> mapBook(List<Book> book);
}
