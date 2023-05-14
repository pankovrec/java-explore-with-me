package ru.practicum.mainService.repository.privates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.mainService.model.Comment;

@Repository
public interface PrivateCommentRepository extends JpaRepository<Comment, Long> {
}
