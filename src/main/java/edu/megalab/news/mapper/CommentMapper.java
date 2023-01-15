package edu.megalab.news.mapper;

import edu.megalab.news.dto.CommentDto;
import edu.megalab.news.dto.request.CommentRequest;
import edu.megalab.news.entity.Comment;
import edu.megalab.news.entity.User;
import edu.megalab.news.exception.NotFoundException;
import edu.megalab.news.repository.PostRepository;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        imports = {NotFoundException.class, LocalDateTime.class})
public abstract class CommentMapper {
    @Autowired
    protected UserMapper userMapper;
    @Autowired
    protected PostRepository postRepository;


    @Mapping(target = "post", expression = "java(postRepository.findById(form.getPostId())\n" +
            ".orElseThrow(() -> new NotFoundException(\"Post with id \" + form.getPostId() + \" not found!\")))")
    @Mapping(target = "createdTime", expression = "java(LocalDateTime.now())")
    @Mapping(target = "parent", source = "parent")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "form.text")
    @Mapping(target = "user", source = "user")
    public abstract Comment toEntity(CommentRequest form, User user, Comment parent);

    @Mapping(target = "user", expression = "java(userMapper.toDto(comment.getUser()))")
    public abstract CommentDto toDto(Comment comment);
}
