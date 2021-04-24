package pl.tboba.repositoryfetcher.model.repository;

import lombok.Data;
import lombok.NoArgsConstructor;

import pl.tboba.repositoryfetcher.model.user.GithubUserState;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Data
public class GithubRepositoryBatch {

    public List<GithubRepository> repositories = Collections.emptyList();

    public GithubUserState userState = GithubUserState.EXISTING;

}
