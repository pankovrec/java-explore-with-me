package ru.practicum.mainService.repository.admins;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.mainService.model.Category;

import java.util.List;

@Repository
public interface CategoryRepositoryAdmin extends JpaRepository<Category, Long> {
    List<Category> findAllByName(String name);
}
