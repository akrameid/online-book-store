package com.example.onlinebookstore.mapper;

import com.example.onlinebookstore.dto.UserBookRequestDto;
import com.example.onlinebookstore.entity.UserBookRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, BookMapper.class})
public interface UserBookRequestMapper {
    UserBookRequestDto requestToDto(final UserBookRequest userBookRequest);

    List<UserBookRequestDto> requestToDto(List<UserBookRequest> requests);

}
