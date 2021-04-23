package pl.tboba.repositoryfetcher.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.tboba.repositoryfetcher.model.GithubRepository;
import pl.tboba.repositoryfetcher.model.GithubUser;

import java.util.List;
import java.util.Optional;

public class DataServiceTests {

    @Test
    @DisplayName("Return correct list of repositories for the guaranteed user test")
    public void testGetUserRepositoriesShouldReturnRepositoriesForValidUser() {
        Optional<List<GithubRepository>> guaranteedRepositories = DataService.getParsedRepositoriesFromUser("tboba");

        Assertions.assertTrue(guaranteedRepositories::isPresent);
    }

    @Test
    @DisplayName("Return empty Optional class of the list of repositories if the user is invalid test")
    public void testGetUserRepositoriesShouldReturnEmptyOptionalForInvalidUser() {
        Optional<List<GithubRepository>> invalidUserRepositories = DataService.getParsedRepositoriesFromUser("IAmAnInvalidUserWhoDoesNotExist");

        Assertions.assertTrue(invalidUserRepositories::isEmpty);
    }

    @Test
    @DisplayName("Return correct response with summarized stargazers for the guaranteed user test")
    public void testGetSummarizedStargazersFromValidUser() {
        Optional<GithubUser> guaranteedUser = DataService.getGithubUser("tboba");

        Assertions.assertTrue(guaranteedUser::isPresent);
    }

    @Test
    @DisplayName("Return empty Optional class of the GithubUser entity if the user is invalid test")
    public void testGetGithubUserShouldReturnEmptyOptionalForInvalidUser() {
        Optional<GithubUser> guaranteedUser = DataService.getGithubUser("IAmAnInvalidUserWhoDoesNotExist");

        Assertions.assertTrue(guaranteedUser::isEmpty);
    }

}
