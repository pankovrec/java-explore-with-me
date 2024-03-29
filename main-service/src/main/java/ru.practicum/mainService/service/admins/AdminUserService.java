package ru.practicum.mainService.service.admins;

import ru.practicum.mainService.model.User;

import java.util.List;

/**
 * UserServiceAdmin
 */
public interface AdminUserService {

    /**
     * Добавить пользователя
     */
    User postUser(User user);

    /**
     * Получить список пользователей
     */
    List<User> getUsers(List<Long> ids, Integer from, Integer size);

    /**
     * Удалить пользователя
     */
    void deleteUser(Long id);
}
