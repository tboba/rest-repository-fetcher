package pl.tboba.repositoryfetcher.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import pl.tboba.repositoryfetcher.model.repository.GithubRepository;
import pl.tboba.repositoryfetcher.model.repository.GithubRepositoryBatch;
import pl.tboba.repositoryfetcher.model.user.GithubUser;
import pl.tboba.repositoryfetcher.model.user.GithubUserState;

import java.util.List;
import java.util.Optional;

@Service
public class DataService {

    /**
     * Get parsed repositories of a provided user from the GitHub REST API.
     * @param username A user from whom the data will be fetched.
     * @return A {@link GithubRepositoryBatch} that will contain the state of the found user
     * and the list of repositories.
     */
    public GithubRepositoryBatch getParsedRepositoriesFromUser(String username) {
        RestTemplate restTemplate = new RestTemplate();
        return getParsedRepositoriesFromUser(restTemplate, username);
    }

    /**
     * Get parsed repositories of a provided user from the GitHub REST API from a given {@link RestTemplate}.
     * @param username A user from whom the data will be fetched.
     * @return A {@link GithubRepositoryBatch} that will contain the state of the found user
     * and the list of repositories.
     */
    public GithubRepositoryBatch getParsedRepositoriesFromUser(RestTemplate restTemplate, String username) {
        GithubRepositoryBatch repositoryBatch = new GithubRepositoryBatch();

        try {
            ResponseEntity<List<GithubRepository>> objectsToParse = restTemplate.exchange(
                    String.format("https://api.github.com/users/%s/repos", username),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<GithubRepository>>() {}
            );

            repositoryBatch.setRepositories(objectsToParse.getBody());
        } catch (HttpStatusCodeException exception) {
            repositoryBatch.setUserState(GithubUserState.NOT_FOUND);
        }

        return repositoryBatch;
    }

    /**
     * Get the computed GitHub user with a name and summarized stargazers of a provided user.
     * @param username A user from whom the data will be fetched and computed.
     * @return The GitHub user's representation wrapped in an {@link Optional}.
     */
    public Optional<GithubUser> getGithubUser(String username) {
        RestTemplate restTemplate = new RestTemplate();
        return getGithubUser(restTemplate, username);
    }

    /**
     * Get the computed GitHub user with a name and summarized stargazers of a provided user from a given {@link RestTemplate}.
     * @param username A user from whom the data will be fetched and computed.
     * @return The GitHub user's representation wrapped in an {@link Optional}.
     */
    public Optional<GithubUser> getGithubUser(RestTemplate restTemplate, String username) {
        GithubRepositoryBatch repositoryList = getParsedRepositoriesFromUser(restTemplate, username);

        if (repositoryList.getUserState() == GithubUserState.NOT_FOUND) {
            return Optional.empty();
        }

        List<GithubRepository> githubRepositories = repositoryList.getRepositories();

        if (githubRepositories == null) {
            return Optional.empty();
        }

        int stargazers = githubRepositories.stream()
                .mapToInt(GithubRepository::getStargazers)
                .sum();

        return Optional.of(new GithubUser(username, stargazers));
    }

}
