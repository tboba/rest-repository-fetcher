package pl.tboba.repositoryfetcher.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.tboba.repositoryfetcher.model.repository.GithubRepository;
import pl.tboba.repositoryfetcher.model.repository.GithubRepositoryBatch;
import pl.tboba.repositoryfetcher.model.user.GithubUserState;
import pl.tboba.repositoryfetcher.service.DataService;
import pl.tboba.repositoryfetcher.model.user.GithubUser;

import java.util.List;
import java.util.Optional;

@RestController
public class GithubRepositoryController {

    private final DataService dataService;

    public GithubRepositoryController(final DataService dataService) {
        this.dataService = dataService;
    }

    /**
     * Returns a JSON response with a full list of repositories of a provided user.
     * @param user A user from whom the data will be fetched.
     * @return A full list of provided user's repositories.
     */
    @RequestMapping(path = "/repos/{user}", method = RequestMethod.GET)
    public ResponseEntity<List<GithubRepository>> getUserRepositories(@PathVariable("user") String user) {
        GithubRepositoryBatch repositoryBatch = dataService.getParsedRepositoriesFromUser(user);

        if (repositoryBatch.getUserState() == GithubUserState.NOT_FOUND) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(repositoryBatch.getRepositories());
    }

    /**
     * Returns a JSON response with a name and summarized stargazers of a provided user.
     * @param user A user from whom the data will be fetched and computed.
     * @return A JSON response with provided user's data.
     */
    @RequestMapping(path = "/stargazers/{user}", method = RequestMethod.GET)
    public ResponseEntity<GithubUser> getUserStargazers(@PathVariable("user") String user) {
        Optional<GithubUser> optionalGithubUser = dataService.getGithubUser(user);

        return optionalGithubUser.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
