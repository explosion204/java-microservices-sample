package com.epam.microserviceslearning.storageservice.security;

import com.epam.microserviceslearning.storageservice.security.client.GithubClient;
import com.epam.microserviceslearning.storageservice.security.model.AccessTokenResponse;
import com.epam.microserviceslearning.storageservice.security.model.GithubUser;
import com.epam.microserviceslearning.storageservice.security.model.User;
import com.epam.microserviceslearning.storageservice.security.model.UserRepository;
import com.epam.microserviceslearning.storageservice.security.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final String USER_ID_CLAIM = "user_id";

    private final GithubClient githubClient;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public AccessTokenResponse getAccessToken(String authorizationCode) {
        final String apiKey = githubClient.getApiKey(authorizationCode)
                .orElseThrow(RuntimeException::new);

        final GithubUser githubUser = githubClient.getUser(apiKey);
        final long userId = githubUser.getId();

        if (userRepository.findById(userId).isEmpty()) {
            initUser(userId);
        }

        final String accessToken = tokenService.generateJwt(
                Map.of(USER_ID_CLAIM, userId)
        );

        return AccessTokenResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    private void initUser(long id) {
        final User newUser = new User();

        newUser.setId(id);
        newUser.setRole(UserRole.USER);

        userRepository.save(newUser);
    }
}
