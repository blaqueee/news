package edu.megalab.news.controller;

import edu.megalab.news.dto.PostDto;
import edu.megalab.news.dto.PostShortDto;
import edu.megalab.news.dto.request.PostRequest;
import edu.megalab.news.entity.User;
import edu.megalab.news.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @DeleteMapping
    public ResponseEntity<?> deletePost(@RequestParam("id") Long postId,
                                        @AuthenticationPrincipal User user) {
        postService.deletePost(postId, user);
        return ResponseEntity.ok("Post has been deleted!");
    }

    @GetMapping
    public ResponseEntity<Page<PostShortDto>> findPosts(@PageableDefault(page = 0, size = 5) Pageable pageable) {
        return ResponseEntity.ok(postService.findNewPosts(pageable));
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<PostShortDto>> filter(@RequestParam(name = "category") List<Long> categoryIds,
                                                     @PageableDefault(page = 0, size = 5) Pageable pageable) {
        return ResponseEntity.ok(postService.filterByCategories(categoryIds, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> findById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(postService.findById(id));
    }
}
