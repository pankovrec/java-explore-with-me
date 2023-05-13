package ru.practicum.mainService.service.publics;

import ru.practicum.mainService.dto.event.CommentDto;

import java.util.List;

/**
 * CommentServicePublic
 */

public interface PublicCommentService {

    /**
     * получить список комментариев события
     */
    List<CommentDto> getAllCommentsByEvent(Long eventId, Integer from, Integer size);

}
