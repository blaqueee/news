package edu.megalab.news.controller;

import edu.megalab.news.dto.CommentDto;
import edu.megalab.news.dto.request.CommentRequest;
import edu.megalab.news.entity.User;
import edu.megalab.news.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentRequest commentForm,
                                                    @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(commentService.createComment(commentForm, user));
    }
}
