package edu.megalab.news.mapper;

import edu.megalab.news.dto.PostDto;
import edu.megalab.news.dto.PostShortDto;
import edu.megalab.news.dto.request.PostRequest;
import edu.megalab.news.entity.Post;
import edu.megalab.news.entity.User;
import edu.megalab.news.exception.NotFoundException;
import edu.megalab.news.repository.CategoryRepository;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
imports = {NotFoundException.class, LocalDateTime.class})
public abstract class PostMapper {
    @Autowired
    protected CategoryRepository categoryRepository;
    @Autowired
    protected UserMapper userMapper;
    @Autowired
    protected CategoryMapper categoryMapper;

    @Mapping(target = "category", expression = "java(categoryRepository.findById(form.getCategoryId())" +
            ".orElseThrow(() -> new NotFoundException(\"Category with id \" + form.getCategoryId() + \" not found!\")))")
    @Mapping(target = "createdTime", expression = "java(LocalDateTime.now())")
    @Mapping(target = "user", source = "user")
    public abstract Post toEntity(PostRequest form, String imageUrl, User user);

    @Mapping(target = "user", expression = "java(userMapper.toDto(post.getUser()))")
    @Mapping(target = "category", expression = "java(categoryMapper.toDto(post.getCategory()))")
    public abstract PostDto toDto(Post post);

    public abstract PostShortDto toShortDto(Post post);
}
