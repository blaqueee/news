package edu.megalab.news.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEditRequest {
    @JsonProperty("avatar")
    private MultipartFile avatar;

    @JsonProperty("first_name")
    @NotBlank
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank
    private String lastName;

    @JsonProperty("username")
    @NotBlank
    private String username;
}
