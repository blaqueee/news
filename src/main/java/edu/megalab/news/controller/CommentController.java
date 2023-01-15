package edu.megalab.news.controller;

import edu.megalab.news.dto.CommentDto;
import edu.megalab.news.dto.request.CommentRequest;
import edu.megalab.news.entity.User;
import edu.megalab.news.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/posts")
    public ResponseEntity<Page<CommentDto>> getByPostId(@RequestParam(name = "id") Long postId,
                                                        @PageableDefault(page = 0, size = 5) Pageable pageable) {
        return ResponseEntity.ok(commentService.findByPostId(postId, pageable));
    }

    @GetMapping
    public ResponseEntity<Page<CommentDto>> getByParentId(@RequestParam(name = "id") Long parentId,
                                                          @PageableDefault(page = 0, size = 5) Pageable pageable) {
        return ResponseEntity.ok(commentService.findByParentId(parentId, pageable));
    }
}
