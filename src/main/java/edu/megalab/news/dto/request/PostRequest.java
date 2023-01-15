package edu.megalab.news.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
    @JsonProperty("image")
    @NotNull
    private MultipartFile image;

    @JsonProperty("title")
    @NotBlank
    private String title;

    @JsonProperty("description")
    @NotBlank
    private String description;

    @JsonProperty("text")
    @NotBlank
    private String text;

    @JsonProperty("category_id")
    @NotNull
    private Long categoryId;
}
