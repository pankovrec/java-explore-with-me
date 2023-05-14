package ru.practicum.mainService.repository.publics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.mainService.model.User;

@Repository
public interface PublicUserRepository extends JpaRepository<User, Long> {
}
