package ru.practicum.mainService.dto.event;

import ru.practicum.mainService.model.Comment;

/**
 * Comment mapper.
 */

public class CommentMapper {
    public static CommentDto toCommentDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getText(), comment.getEvent().getId(), comment.getAuthor().getName(), comment.getCreated(), comment.getModified());
    }
}
