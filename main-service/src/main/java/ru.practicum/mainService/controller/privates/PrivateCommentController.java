package ru.practicum.mainService.controller.privates;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainService.dto.event.CommentDto;
import ru.practicum.mainService.dto.event.NewCommentDto;
import ru.practicum.mainService.service.privates.PrivateCommentService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Comment controller admin
 */

@RestController
public class PrivateCommentController {
    private final PrivateCommentService commentService;

    public PrivateCommentController(PrivateCommentService commentService) {
        this.commentService = commentService;
    }

    @Transactional
    @PostMapping(path = "/users/{userId}/events/{eventId}/comments")
    public ResponseEntity<CommentDto> postComment(@NotNull @Positive @PathVariable(name = "userId") Long userId,
                                                  @NotNull @Positive @PathVariable(name = "eventId") Long eventId,
                                                  @Valid @RequestBody NewCommentDto comment) {
        CommentDto savedComment = commentService.postComment(userId, eventId, comment);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }

    @Transactional
    @PatchMapping(path = "/users/{userId}/comments/{commentId}")
    public CommentDto patchComment(@NotNull @Positive @PathVariable(name = "userId") Long userId,
                                   @NotNull @Positive @PathVariable(name = "commentId") Long commentId,
                                   @Valid @RequestBody CommentDto comment) {
        return commentService.patchComment(userId, commentId, comment);
    }

    @Transactional
    @DeleteMapping(path = "/users/{userId}/comments/{commentId}")
    public ResponseEntity<HttpStatus> deleteComment(@NotNull @Positive @PathVariable(name = "userId") Long userId,
                                                    @NotNull @Positive @PathVariable(name = "commentId") Long commentId) {
        commentService.deleteComment(userId, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
