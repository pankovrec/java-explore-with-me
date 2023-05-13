package ru.practicum.mainService.service.privates;

import ru.practicum.mainService.dto.event.CommentDto;
import ru.practicum.mainService.dto.event.NewCommentDto;

/**
 * CommentServicePrivate
 */
public interface PrivateCommentService {

    /**
     * добавить комментарий
     */
    CommentDto postComment(Long userId, Long eventId, NewCommentDto comment);

    /**
     * добавить комментарий
     */
    CommentDto patchComment(Long userId, Long commentId, CommentDto comment);

    /**
     * удалить свой комментарий
     */
    void deleteComment(Long userId, Long commentId);
}
