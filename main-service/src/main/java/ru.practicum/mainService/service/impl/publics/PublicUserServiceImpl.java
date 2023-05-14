package ru.practicum.mainService.service.impl.publics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.mainService.error.exception.NotFoundUserException;
import ru.practicum.mainService.model.User;
import ru.practicum.mainService.repository.publics.PublicUserRepository;
import ru.practicum.mainService.service.publics.PublicUserService;

/**
 * UserServicePublicImpl
 */

@Service
public class PublicUserServiceImpl implements PublicUserService {

    private final PublicUserRepository repository;

    @Autowired
    public PublicUserServiceImpl(PublicUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User getUser(Long userId) {
        return repository.findById(userId).orElseThrow(() -> new NotFoundUserException(String.format("User id=%s not found", userId)));
    }
}
