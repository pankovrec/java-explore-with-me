package ru.practicum.mainService.service.impl.admins;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ru.practicum.mainService.error.exception.UserAlreadyExistException;
import ru.practicum.mainService.model.User;
import ru.practicum.mainService.repository.admins.UserRepositoryAdmin;
import ru.practicum.mainService.service.admins.UserServiceAdmin;

import java.util.List;
import java.util.Optional;

/**
 * UserServiceAdminImpl
 */

@Service
public class UserServiceAdminImpl implements UserServiceAdmin {

    private final UserRepositoryAdmin repository;

    @Autowired
    public UserServiceAdminImpl(UserRepositoryAdmin repository) {
        this.repository = repository;
    }

    @Override
    public User postUser(User user) {
        User foundedUser = repository.findByName(user.getName());
        if (foundedUser != null) {
            throw new UserAlreadyExistException("Имя должно быть уникально " + user.getName());
        }
        return repository.save(user);
    }

    @Override
    public List<User> getUsers(List<Long> ids, Integer from, Integer size) {
        Pageable page = PageRequest.of((from / size), size);
        List<User> users;
        if (ids != null) {
            users = repository.findAllByIdIn(ids, page);
        } else {
            users = repository.findAll(page).toList();
        }
        return users;
    }

    @Override
    public void delete(Long id) {
        Optional<User> user = repository.findById(id);
        repository.deleteById(id);
    }
}
