package ru.practicum.mainService.repository.admins;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainService.model.User;

import java.util.List;

public interface UserRepositoryAdmin extends JpaRepository<User, Long> {

    List<User> findAllByIdIn(List<Long> ids, Pageable pageable);

    User findByName(String name);

    User findByEmail(String email);

}
