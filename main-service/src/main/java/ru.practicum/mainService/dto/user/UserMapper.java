package ru.practicum.mainService.dto.user;

import ru.practicum.mainService.model.User;

/**
 * UserMapper
 */

public class UserMapper {
    public static User fromUserDto(UserDto userDto) {
        return new User()
                .setName(userDto.getName())
                .setEmail(userDto.getEmail());
    }

    public static UserDto toUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
