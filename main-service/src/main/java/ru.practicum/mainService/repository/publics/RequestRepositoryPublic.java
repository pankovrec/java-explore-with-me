package ru.practicum.mainService.repository.publics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.mainService.model.Request;

import java.util.List;

@Repository
public interface RequestRepositoryPublic extends JpaRepository<Request, Long> {

    List<Request> findAllByIdIn(List<Long> ids);

    List<Request> findAllByEventId(Long eventId);

}
