package ru.practicum.mainService.dto.event;

import ru.practicum.mainService.model.Comment;

/**
 * Comment mapper.
 */

public class CommentMapper {
    public static CommentDto toCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        commentDto.setCreated(comment.getCreated());
        commentDto.setAuthor(comment.getAuthor().getName());
        commentDto.setEvent(comment.getEvent().getId());
        return commentDto;
    }
}
