package edu.megalab.news.service;

import edu.megalab.news.dto.FileDto;
import edu.megalab.news.dto.PostDto;
import edu.megalab.news.dto.request.PostRequest;
import edu.megalab.news.entity.Post;
import edu.megalab.news.entity.User;
import edu.megalab.news.exception.FileException;
import edu.megalab.news.exception.FileWriteException;
import edu.megalab.news.exception.GCPFileUploadException;
import edu.megalab.news.mapper.PostMapper;
import edu.megalab.news.repository.PostRepository;
import edu.megalab.news.utility.CloudStorageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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


}
