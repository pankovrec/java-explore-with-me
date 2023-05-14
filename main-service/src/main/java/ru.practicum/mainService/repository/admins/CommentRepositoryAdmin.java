package ru.practicum.mainService.repository.admins;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.mainService.model.Comment;

import java.util.List;

@Repository
public interface CommentRepositoryAdmin extends JpaRepository<Comment, Long> {
    List<Comment> findAllByAuthor_Id(Long userId, Pageable pageable);
}
