package edu.megalab.news.service;

import edu.megalab.news.dto.PostShortDto;
import edu.megalab.news.entity.Bookmark;
import edu.megalab.news.entity.Post;
import edu.megalab.news.entity.User;
import edu.megalab.news.exception.NotFoundException;
import edu.megalab.news.mapper.BookmarkMapper;
import edu.megalab.news.mapper.PostMapper;
import edu.megalab.news.repository.BookmarkRepository;
import edu.megalab.news.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final PostRepository postRepository;
    private final BookmarkMapper bookmarkMapper;
    private final PostMapper postMapper;

    public PostShortDto addPostToBookmark(Long postId, User user) throws NotFoundException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post with id " + postId + " not found!"));
        Bookmark bookmark = bookmarkMapper.toEntity(post, user);
        Bookmark saved = bookmarkRepository.save(bookmark);
        return postMapper.toShortDto(saved.getPost());
    }

    public void deleteBookFromBookmark(Long postId, User user) throws NotFoundException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post with id " + postId + " not found!"));
        Bookmark bookmark = bookmarkRepository.findByPostAndUser(post, user)
                .orElseThrow(() -> new NotFoundException("Post with id " + postId + " not found in bookmarks!"));
        bookmarkRepository.delete(bookmark);
    }

    public Page<PostShortDto> getPosts(User user, Pageable pageable) {
        var bookmarks = bookmarkRepository.findByUser(user, pageable);
        return bookmarks.map(Bookmark::getPost)
                .map(postMapper::toShortDto);
    }
}
