package ru.practicum.mainService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainService.model.Comment;

import java.util.Set;

/**
 * Comment repository.
 */

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Set<Comment> findCommentsByEvent_Id(Long eventId);
}
