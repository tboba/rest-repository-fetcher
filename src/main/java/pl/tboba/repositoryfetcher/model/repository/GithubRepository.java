package pl.tboba.repositoryfetcher.model.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.jackson.JsonComponent;


@JsonComponent
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GithubRepository {

    @JsonProperty("name")
    private String name;

    @JsonProperty("stargazers_count")
    private int stargazers;

}
