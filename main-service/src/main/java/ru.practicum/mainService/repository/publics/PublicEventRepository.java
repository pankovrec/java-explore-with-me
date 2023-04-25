package ru.practicum.mainService.repository.publics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.practicum.mainService.model.Event;

@Repository
public interface PublicEventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event> {

}
