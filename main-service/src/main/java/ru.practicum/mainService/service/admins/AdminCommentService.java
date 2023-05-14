package ru.practicum.mainService.service.admins;

import ru.practicum.mainService.dto.event.CommentDto;

import java.util.List;

public interface AdminCommentService {

    /**
     * получить список комментариев конкретного пользователя
     */
    List<CommentDto> getAllCommentsByUser(Long userId, Integer from, Integer size);

    /**
     * удалить комментарий по id
     */
    void deleteComment(Long commentId);
}
