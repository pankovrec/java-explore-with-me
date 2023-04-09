package ru.practicum.mainService.dto.user;

import ru.practicum.mainService.model.User;

/**
 * UserMapper
 */

public class UserMapper {

    public static User fromUserDto(UserDto dto) {
        return new User()
                .setName(dto.getName())
                .setEmail(dto.getEmail());
    }

    public static UserDto toUserDto(User entity) {
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        return dto;
    }
}
