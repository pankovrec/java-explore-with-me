package ru.practicum.mainService.repository.admins;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.practicum.mainService.model.Event;

import java.util.List;

@Repository
public interface AdminEventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event> {
    List<Event> findAllByCategoryId(Long categoryId);

    List<Event> findAllByIdIn(List<Long> ids);
}
