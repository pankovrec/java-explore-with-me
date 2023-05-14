package ru.practicum.mainService.repository.publics;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.mainService.model.Comment;

import java.util.List;

@Repository
public interface PublicCommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByEventId(Long eventId, Pageable pageable);
}
