package edu.megalab.news.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("text")
    private String text;

    @JsonProperty("user")
    private UserDto user;

    @JsonProperty("category")
    private CategoryDto category;

    @JsonProperty("created_time")
    private LocalDateTime createdTime;
}
