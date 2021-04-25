package pl.tboba.repositoryfetcher.service;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.tboba.repositoryfetcher.model.repository.GithubRepository;
import pl.tboba.repositoryfetcher.model.repository.GithubRepositoryBatch;
import pl.tboba.repositoryfetcher.model.user.GithubUser;
import pl.tboba.repositoryfetcher.model.user.GithubUserState;

import java.util.List;
import java.util.Optional;

public class DataServiceTests {

    private final DataService dataService = new DataService();

    @Test
    @DisplayName("Return correct batch of repositories for a guaranteed user test")
    public void testGetUserRepositoriesShouldReturnRepositoriesForValidUser() {
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);

        List<GithubRepository> repositorySet = Lists.newArrayList(
                new GithubRepository("A", 15),
                new GithubRepository("B", 3)
        );

        Mockito.when(restTemplate.exchange(
                "https://api.github.com/users/existingUser/repos",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<GithubRepository>>() {})
        ).thenReturn(new ResponseEntity<>(repositorySet, HttpStatus.OK));

        GithubRepositoryBatch repositoryBatch = dataService.getParsedRepositoriesFromUser(restTemplate, "existingUser");

        Assertions.assertSame(GithubUserState.EXISTING, repositoryBatch.getUserState());
        Assertions.assertEquals(repositorySet, repositoryBatch.getRepositories());
    }

    @Test
    @DisplayName("Return empty batch of repositories if the user is not registered test")
    public void testGetUserRepositoriesShouldReturnEmptyOptionalForInvalidUser() {
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);

        Mockito.when(restTemplate.exchange(
                "https://api.github.com/users/fakeUser/repos",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<GithubRepository>>() {})
        ).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        GithubRepositoryBatch repositoryBatch = dataService.getParsedRepositoriesFromUser(restTemplate, "fakeUser");
        Assertions.assertNull(repositoryBatch.getRepositories());
    }

    @Test
    @DisplayName("Return correct response with summarized stargazers for the guaranteed user test")
    public void testGetSummarizedStargazersFromValidUser() {
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);

        List<GithubRepository> repositorySet = Lists.newArrayList(
                new GithubRepository("A", 15),
                new GithubRepository("B", 3)
        );

        Mockito.when(restTemplate.exchange(
                "https://api.github.com/users/existingUser/repos",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<GithubRepository>>() {})
        ).thenReturn(new ResponseEntity<>(repositorySet, HttpStatus.OK));

        Optional<GithubUser> guaranteedUser = dataService.getGithubUser(restTemplate, "existingUser");

        Assertions.assertTrue(guaranteedUser::isPresent);
        Assertions.assertSame(18, guaranteedUser.get().getStargazers());
    }

    @Test
    @DisplayName("Return empty Optional class of the GithubUser entity if the user is invalid test")
    public void testGetGithubUserShouldReturnEmptyOptionalForInvalidUser() {
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);

        Mockito.when(restTemplate.exchange(
                "https://api.github.com/users/fakeUser/repos",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<GithubRepository>>() {})
        ).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        Optional<GithubUser> guaranteedUser = dataService.getGithubUser(restTemplate, "fakeUser");

        Assertions.assertFalse(guaranteedUser::isPresent);
    }

}
