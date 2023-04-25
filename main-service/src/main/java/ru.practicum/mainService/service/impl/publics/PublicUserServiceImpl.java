package ru.practicum.mainService.service.impl.publics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.mainService.model.User;
import ru.practicum.mainService.repository.publics.UserRepositoryPublic;
import ru.practicum.mainService.service.publics.PublicUserService;

import java.util.Optional;

/**
 * UserServicePublicImpl
 */

@Service
public class PublicUserServiceImpl implements PublicUserService {

    private final UserRepositoryPublic repository;

    @Autowired
    public PublicUserServiceImpl(UserRepositoryPublic repository) {
        this.repository = repository;
    }

    @Override
    public User getUser(Long userId) {
        Optional<User> user = repository.findById(userId);
        return user.get();
    }
}
