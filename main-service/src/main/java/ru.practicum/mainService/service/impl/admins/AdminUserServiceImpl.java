package ru.practicum.mainService.service.impl.admins;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.mainService.error.exception.ExistUserException;
import ru.practicum.mainService.model.User;
import ru.practicum.mainService.repository.admins.AdminUserRepository;
import ru.practicum.mainService.service.admins.AdminUserService;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * UserServiceAdminImpl
 */

@Service
public class AdminUserServiceImpl implements AdminUserService {

    private final AdminUserRepository repository;

    @Autowired
    public AdminUserServiceImpl(AdminUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User postUser(User user) {
        User check = repository.findByName(user.getName());
        if (check != null) {
            throw new ExistUserException("Имя должно быть уникально " + user.getName());
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
    public void deleteUser(Long id) {
        User check = repository.findById(id).orElseThrow(NoSuchElementException::new);
        repository.deleteById(id);
    }
}
