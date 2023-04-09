package ru.practicum.mainService.repository.publics;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.mainService.model.Event;

import java.util.List;

@Repository
public interface EventRepositoryPublic extends JpaRepository<Event, Long> {
    List<Event> findAll(Specification<Event> specification, Pageable pageable);
}
