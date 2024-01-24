package com.example.onlinebookstore.mapper;

import com.example.onlinebookstore.dto.UserDto;
import com.example.onlinebookstore.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "createdAt", source = "createdAt")
    UserDto mapToDto(User user);

    List<UserDto> mapToDto(List<User> user);

    @Mapping(target = "createdAt", source = "createdAt")
    User map(UserDto userDto);

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
