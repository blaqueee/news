package edu.megalab.news.controller;

import edu.megalab.news.dto.PostShortDto;
import edu.megalab.news.entity.User;
import edu.megalab.news.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @PostMapping
    public ResponseEntity<PostShortDto> addPostToBookmark(@RequestParam(name = "post") Long postId,
                                                          @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(bookmarkService.addPostToBookmark(postId, user));
    }

    @DeleteMapping
    public ResponseEntity<?> deletePostFromBookmark(@RequestParam(name = "post") Long postId,
                                                    @AuthenticationPrincipal User user) {
        bookmarkService.deleteBookFromBookmark(postId, user);
        return ResponseEntity.ok("Post has been removed!");
    }

    @GetMapping
    public ResponseEntity<Page<PostShortDto>> getPostsFromBookmark(@AuthenticationPrincipal User user,
                                                                   @PageableDefault(page = 0, size = 5) Pageable pageable) {
        return ResponseEntity.ok(bookmarkService.getPosts(user, pageable));
    }
}
