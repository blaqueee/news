package edu.megalab.news.mapper;

import edu.megalab.news.dto.CategoryDto;
import edu.megalab.news.entity.Category;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class CategoryMapper {
    public abstract CategoryDto toDto(Category category);
}
