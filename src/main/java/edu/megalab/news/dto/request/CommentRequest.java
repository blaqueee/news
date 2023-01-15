package edu.megalab.news.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    @JsonProperty("text")
    @NotBlank
    private String text;

    @JsonProperty("parent_id")
    private Long parentId;

    @JsonProperty("post_id")
    @NotNull
    private Long postId;
}
