package edu.megalab.news.service;

import edu.megalab.news.dto.FileDto;
import edu.megalab.news.dto.PostDto;
import edu.megalab.news.dto.PostShortDto;
import edu.megalab.news.dto.request.PostRequest;
import edu.megalab.news.entity.Post;
import edu.megalab.news.entity.User;
import edu.megalab.news.exception.*;
import edu.megalab.news.mapper.PostMapper;
import edu.megalab.news.repository.PostRepository;
import edu.megalab.news.utility.CloudStorageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final CloudStorageUtil cloudStorage;

    public PostDto createPost(PostRequest form, User user) throws FileException, FileWriteException, GCPFileUploadException {
        if (!cloudStorage.isImageFile(form.getImage()))
            throw new FileException("Not image file has been uploaded!");
        FileDto file = cloudStorage.uploadFile(form.getImage());
        Post post = postMapper.toEntity(form, file.getLink(), user);
        Post saved = postRepository.save(post);
        return postMapper.toDto(saved);
    }

    public Page<PostShortDto> findNewPosts(Pageable pageable) {
        var posts = postRepository.findByOrderByCreatedTimeDesc(pageable);
        return posts.map(postMapper::toShortDto);
    }

    public Page<PostShortDto> filterByCategories(List<Long> categoryIds, Pageable pageable) {
        var posts = postRepository.filterByCategories(categoryIds, pageable);
        return posts.map(postMapper::toShortDto);
    }

    public PostDto findById(Long id) throws NotFoundException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post with id " + id + " not found!"));
        return postMapper.toDto(post);
    }

    public void deletePost(Long id, User user) throws NotFoundException, NotPermittedException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post with id " + id + " not found!"));
        if (!post.getUser().getId().equals(user.getId()))
            throw new NotPermittedException("You can't delete others' posts!");
        postRepository.delete(post);
    }
}
