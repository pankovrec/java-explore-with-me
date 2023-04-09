package ru.practicum.mainService.service.impl.publics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import ru.practicum.mainService.error.exception.UserNotFoundException;
import ru.practicum.mainService.model.User;
import ru.practicum.mainService.repository.publics.UserRepositoryPublic;
import ru.practicum.mainService.service.publics.UserServicePublic;

import java.util.Optional;

/**
 * UserServicePublicImpl
 */

@Service
public class UserServicePublicImpl implements UserServicePublic {

    private final UserRepositoryPublic repository;

    @Autowired
    public UserServicePublicImpl(UserRepositoryPublic repository) {
        this.repository = repository;
    }

    @Override
    public User getUser(Long userId) {
        Optional<User> user = repository.findById(userId);
        return user.get();
    }
}
