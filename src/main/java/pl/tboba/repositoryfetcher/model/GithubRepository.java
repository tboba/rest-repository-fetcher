package pl.tboba.repositoryfetcher.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.jackson.JsonComponent;

import javax.validation.constraints.NotBlank;

@JsonComponent
@NoArgsConstructor
@Data
public class GithubRepository {
    
    @NotBlank(message = "Repository name cannot be empty!")
    @JsonProperty("name")
    private String name;

    @JsonProperty("stargazers_count")
    private int stargazers;

}
