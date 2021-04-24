package pl.tboba.repositoryfetcher.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.springframework.boot.jackson.JsonComponent;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonComponent
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GithubUser {

    private String username;

    @JsonProperty("stargazers_count")
    private int stargazers;

}
