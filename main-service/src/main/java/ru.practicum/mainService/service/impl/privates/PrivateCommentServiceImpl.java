package ru.practicum.mainService.service.impl.privates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.mainService.dto.event.CommentDto;
import ru.practicum.mainService.dto.event.CommentMapper;
import ru.practicum.mainService.dto.event.NewCommentDto;
import ru.practicum.mainService.error.exception.NotFoundEventException;
import ru.practicum.mainService.error.exception.RequestInvalidException;
import ru.practicum.mainService.model.Comment;
import ru.practicum.mainService.model.Event;
import ru.practicum.mainService.model.State;
import ru.practicum.mainService.model.User;
import ru.practicum.mainService.repository.privates.PrivateCommentRepository;
import ru.practicum.mainService.repository.publics.PublicEventRepository;
import ru.practicum.mainService.service.privates.PrivateCommentService;
import ru.practicum.mainService.service.publics.PublicUserService;

import java.time.LocalDateTime;

/**
 * CommentServicePrivateImpl
 */
@Service
public class PrivateCommentServiceImpl implements PrivateCommentService {
    private final PublicUserService userService;
    private final PrivateCommentRepository repository;

    private final PublicEventRepository eventRepository;

    @Autowired
    public PrivateCommentServiceImpl(PublicUserService userService, PrivateCommentRepository repository, PublicEventRepository eventRepository) {
        this.userService = userService;
        this.repository = repository;
        this.eventRepository = eventRepository;
    }


    @Override
    public CommentDto postComment(Long userId, Long eventId, NewCommentDto comment) {
        User user = userService.getUser(userId);
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundEventException(String.format("Event id=%s not found", eventId)));

        if (event.getState() != State.PUBLISHED) {
            throw new RequestInvalidException("Статус должен быть PUBLISHED");
        }

        Comment newComment = new Comment()
                .setAuthor(user)
                .setCreated(LocalDateTime.now())
                .setEvent(event)
                .setText(comment.getText());

        return CommentMapper.toCommentDto(repository.save(newComment));
    }

    @Override
    public CommentDto patchComment(Long userId, Long commentId, CommentDto comment) {
        Comment checkComment = getCheckComment(userId, commentId);
        Comment patching = new Comment();
        patching.setAuthor(checkComment.getAuthor());
        patching.setEvent(checkComment.getEvent());
        patching.setText(comment.getText());
        patching.setCreated(LocalDateTime.now());
        Comment patched = repository.save(patching);
        return CommentMapper.toCommentDto(patched);
    }

    @Override
    public void deleteComment(Long userId, Long commentId) {
        User checkUser = userService.getUser(userId);
        Comment checkComment = getCheckComment(userId, commentId);
        repository.delete(checkComment);
    }

    private Comment getCheckComment(Long userId, Long commentId) {
        Comment checkComment = repository.findById(commentId).orElseThrow(() -> new NotFoundEventException(String.format("Comment id=%s not found", commentId)));
        if (!checkComment.getAuthor().getId().equals(userId)) {
            throw new RequestInvalidException("You're not author of this comment");
        }
        return checkComment;
    }
}

