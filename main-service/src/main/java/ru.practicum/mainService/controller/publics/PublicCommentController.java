package ru.practicum.mainService.controller.publics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.mainService.dto.event.CommentDto;
import ru.practicum.mainService.service.publics.PublicCommentService;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * Comment controller public
 */

@RestController
public class PublicCommentController {

    private final PublicCommentService commentService;

    @Autowired
    public PublicCommentController(PublicCommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping(path = "/events/{eventId}/comments")
    public List<CommentDto> getAllCommentsByEvent(@Positive @NotNull @PathVariable(name = "eventId") Long eventId,
                                                  @Positive @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                  @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return commentService.getAllCommentsByEvent(eventId, from, size);
    }
}
