package ru.practicum.mainService.controller.admins;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainService.dto.event.CommentDto;
import ru.practicum.mainService.service.admins.AdminCommentService;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * Comment controller admin
 */

@RestController
@RequestMapping("/admin")
public class AdminCommentController {

    private final AdminCommentService commentService;

    @Autowired
    public AdminCommentController(AdminCommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping(path = "/users/{userId}/comments")
    public List<CommentDto> getAllCommentsByUser(@Positive @NotNull @PathVariable(name = "userId") Long userId,
                                                 @Positive @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                 @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return commentService.getAllCommentsByUser(userId, from, size);
    }

    @DeleteMapping(path = "/comments/{commentId}")
    public ResponseEntity<HttpStatus> deleteComment(@Positive @NotNull @PathVariable(name = "commentId") Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}