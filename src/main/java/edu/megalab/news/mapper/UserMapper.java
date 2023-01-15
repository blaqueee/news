package edu.megalab.news.mapper;

import edu.megalab.news.dto.JwtDto;
import edu.megalab.news.dto.UserDto;
import edu.megalab.news.dto.request.RegisterRequest;
import edu.megalab.news.dto.request.UserEditRequest;
import edu.megalab.news.entity.User;
import edu.megalab.news.exception.NotFoundException;
import edu.megalab.news.repository.RoleRepository;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
imports = {List.class, NotFoundException.class})
public abstract class UserMapper {
    @Autowired
    protected RoleRepository roleRepository;

    @Mapping(target = "roles", expression = "java(List.of(roleRepository.findByName(\"ROLE_USER\")" +
            ".orElseThrow(() -> new NotFoundException(\"Role with name ROLE_USER not found!\"))))")
    @Mapping(target = "avatarUrl", constant = "https://storage.googleapis.com/megalab-news/user.png")
    public abstract User toEntity(RegisterRequest registerRequest);

    public User toEntity(UserEditRequest form, String avatarUrl, User user) {
        user.setAvatarUrl(avatarUrl);
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setUsername(form.getUsername());
        return user;
    }

    public abstract UserDto toDto(User user);

    public abstract JwtDto toJwtDto(User user, String accessToken, String tokenType);
}
