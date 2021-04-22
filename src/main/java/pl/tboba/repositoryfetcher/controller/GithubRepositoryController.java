package pl.tboba.repositoryfetcher.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.tboba.repositoryfetcher.helper.DataFetcher;
import pl.tboba.repositoryfetcher.model.GithubRepository;
import pl.tboba.repositoryfetcher.model.GithubUser;

import java.util.List;
import java.util.Optional;

@RestController
public class GithubRepositoryController {

    @RequestMapping(path = "/repos/{user}", method = RequestMethod.GET)
    public ResponseEntity<List<GithubRepository>> getUserRepositories(@PathVariable("user") String user) {
        Optional<List<GithubRepository>> optionalRepositoryList = DataFetcher.getParsedRepositoriesFromUser(user);

        return optionalRepositoryList.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(path = "/stargazers/{user}", method = RequestMethod.GET)
    public ResponseEntity<GithubUser> getUserStargazers(@PathVariable("user") String user) {
        Optional<GithubUser> optionalGithubUser = DataFetcher.getGithubUser(user);

        return optionalGithubUser.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
