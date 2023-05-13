package ru.practicum.mainService.service.impl.publics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.mainService.dto.event.CommentDto;
import ru.practicum.mainService.dto.event.CommentMapper;
import ru.practicum.mainService.model.Comment;
import ru.practicum.mainService.repository.publics.PublicCommentRepository;
import ru.practicum.mainService.service.publics.PublicCommentService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CommentServicePublicImpl
 */

@Service
public class PublicCommentServiceImpl implements PublicCommentService {

    private final PublicCommentRepository repository;

    @Autowired
    public PublicCommentServiceImpl(PublicCommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CommentDto> getAllCommentsByEvent(Long eventId, Integer from, Integer size) {
        Pageable pageRequest = PageRequest.of((from / size), size);
        List<Comment> comments = repository.findAllByEventId(eventId, pageRequest);
        return comments.stream().map(CommentMapper::toCommentDto).collect(Collectors.toList());
    }
}
