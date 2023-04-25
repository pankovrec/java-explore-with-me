package ru.practicum.mainService.controller.admins;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainService.dto.user.UserDto;
import ru.practicum.mainService.dto.user.UserMapper;
import ru.practicum.mainService.model.User;
import ru.practicum.mainService.service.admins.AdminUserService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User controller admin
 */

@RestController
@RequestMapping("/admin/users")
@Slf4j
@Validated

public class AdminUserController {

    private final AdminUserService userService;

    @Autowired
    public AdminUserController(AdminUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getUsers(@NotNull @RequestParam(name = "ids") List<Long> ids,
                                  @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                  @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get list users ids={} from={}, size={}", ids, from, size);
        return userService.getUsers(ids, from, size)
                .stream().map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<UserDto> postUser(@Valid @RequestBody UserDto userDto) {
        User user = UserMapper.fromUserDto(userDto);
        User newUser = userService.postUser(user);
        UserDto newUserDto = UserMapper.toUserDto(newUser);
        log.info("Post user {}", newUser);
        return new ResponseEntity<>(newUserDto, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{userId}")
    @Transactional
    public ResponseEntity deleteUser(@Positive @PathVariable(name = "userId") Long id) {
        userService.delete(id);
        log.info("Delete user with id={}", id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
