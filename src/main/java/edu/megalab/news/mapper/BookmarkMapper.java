package edu.megalab.news.mapper;

import edu.megalab.news.entity.Bookmark;
import edu.megalab.news.entity.Post;
import edu.megalab.news.entity.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class BookmarkMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    public abstract Bookmark toEntity(Post post, User user);
}
