package ru.practicum.mainService.service.publics;

import ru.practicum.mainService.model.User;

/**
 * UserServicePublic
 */
public interface PublicUserService {

    /**
     * получить пользователя по id
     */
    User getUser(Long userId);
}
