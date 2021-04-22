package pl.tboba.repositoryfetcher.helper;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import pl.tboba.repositoryfetcher.model.GithubRepository;
import pl.tboba.repositoryfetcher.model.GithubUser;

import java.util.List;
import java.util.Optional;

public class DataFetcher {

    public static Optional<List<GithubRepository>> getParsedRepositoriesFromUser(String username) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<GithubRepository>> objectsToParse;

        try {
            objectsToParse = restTemplate.exchange(
                    String.format("https://api.github.com/users/%s/repos", username),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<GithubRepository>>() {}
            );
        } catch (HttpStatusCodeException exception) {
            return Optional.empty();
        }

        return Optional.ofNullable(objectsToParse.getBody());
    }

    public static Optional<GithubUser> getGithubUser(String username) {
        Optional<List<GithubRepository>> optionalRepositoryList = getParsedRepositoriesFromUser(username);

        if (!optionalRepositoryList.isPresent()) {
            return Optional.empty();
        }

        List<GithubRepository> githubRepositories = optionalRepositoryList.get();

        int stargazers = githubRepositories.stream()
                .mapToInt(GithubRepository::getStargazers)
                .sum();

        return Optional.of(new GithubUser(username, stargazers));
    }

    private DataFetcher() {}

}
