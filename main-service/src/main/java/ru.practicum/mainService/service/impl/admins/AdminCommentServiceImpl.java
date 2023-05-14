package ru.practicum.mainService.service.impl.admins;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.mainService.dto.event.CommentDto;
import ru.practicum.mainService.dto.event.CommentMapper;
import ru.practicum.mainService.model.Comment;
import ru.practicum.mainService.repository.admins.CommentRepositoryAdmin;
import ru.practicum.mainService.service.admins.AdminCommentService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * CommentServiceAdminImpl
 */

@Service
public class AdminCommentServiceImpl implements AdminCommentService {

    private final CommentRepositoryAdmin repository;

    @Autowired
    public AdminCommentServiceImpl(CommentRepositoryAdmin repository) {
        this.repository = repository;
    }

    @Override
    public List<CommentDto> getAllCommentsByUser(Long userId, Integer from, Integer size) {
        Pageable page = PageRequest.of((from / size), size);
        List<Comment> comments = repository.findAllByAuthor_Id(userId, page);
        return comments.stream().map(CommentMapper::toCommentDto).collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Long commentId) {
        repository.delete(repository.findById(commentId).orElseThrow(NoSuchElementException::new));
    }
}
