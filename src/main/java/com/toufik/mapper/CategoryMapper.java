package com.toufik.mapper;

import com.toufik.dto.CategoryDto;
import com.toufik.model.Category;
import com.toufik.model.Post;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(category.getPosts()))")
    CategoryDto mapcategoryToDto(Category category);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Category mapDtoTocategory(CategoryDto categoryDto);
}
