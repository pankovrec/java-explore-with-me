package ru.practicum.mainService.repository.publics;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainService.model.User;

public interface UserRepositoryPublic extends JpaRepository<User, Long> {
}
