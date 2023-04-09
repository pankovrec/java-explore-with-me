package ru.practicum.mainService.repository.privates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.mainService.model.Request;

import java.util.List;

@Repository
public interface RequestRepositoryPrivate extends JpaRepository<Request, Long> {

    List<Request> findAllByRequesterId(Long requesterId);

    List<Request> findAllByRequesterIdAndEventId(Long requesterId, Long eventId);

}
