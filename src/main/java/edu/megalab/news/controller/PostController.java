package edu.megalab.news.controller;

import edu.megalab.news.dto.PostDto;
import edu.megalab.news.dto.request.PostRequest;
import edu.megalab.news.entity.User;
import edu.megalab.news.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @ModelAttribute PostRequest postRequest,
                                              @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(postService.createPost(postRequest, user));
    }
}
