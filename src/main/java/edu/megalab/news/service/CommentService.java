package edu.megalab.news.service;

import edu.megalab.news.dto.CommentDto;
import edu.megalab.news.dto.request.CommentRequest;
import edu.megalab.news.entity.Comment;
import edu.megalab.news.entity.Post;
import edu.megalab.news.entity.User;
import edu.megalab.news.exception.NotFoundException;
import edu.megalab.news.mapper.CommentMapper;
import edu.megalab.news.repository.CommentRepository;
import edu.megalab.news.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;

    public CommentDto createComment(CommentRequest form, User user) throws NotFoundException {
        Comment parentComment = getParent(form.getParentId());
        Comment comment = commentMapper.toEntity(form, user, parentComment);
        Comment saved = commentRepository.save(comment);
        return commentMapper.toDto(saved);
    }

    private Comment getParent(Long parentId) throws NotFoundException {
        if (parentId == null)
            return null;
        return commentRepository.findById(parentId)
            .orElseThrow(() -> new NotFoundException("Comment with id " + parentId + " not found!"));
    }

    public Page<CommentDto> findByPostId(Long postId, Pageable pageable) throws NotFoundException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post with id " + postId + " not found!"));
        var comments = commentRepository.findByPostAndParentIsNull(post, pageable);
        return comments.map(commentMapper::toDto);
    }

    public Page<CommentDto> findByParentId(Long parentId, Pageable pageable) throws NotFoundException {
        Comment parent = commentRepository.findById(parentId)
                .orElseThrow(() -> new NotFoundException("Comment with id " + parentId + " not found!"));
        var comments = commentRepository.findByParent(parent, pageable);
        return comments.map(commentMapper::toDto);
    }
}
