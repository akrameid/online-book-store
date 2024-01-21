package com.example.onlinebookstore.mapper;

import com.example.onlinebookstore.dto.BookDto;
import com.example.onlinebookstore.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "createdAt", source = "createdAt")
    BookDto mapToDto(Book book);

    List<BookDto> mapToDto(List<Book> book);

    @Mapping(target = "createdAt", source = "createdAt")
    Book map(BookDto bookDto);

    default LocalDateTime mapCreatedAtToCreatedAt(final Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }

    default Timestamp mapCreatedAtToCreatedAt(final LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Timestamp.valueOf(localDateTime);
    }
}
