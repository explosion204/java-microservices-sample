package com.epam.microserviceslearning.storageservice.security;

import com.epam.microserviceslearning.storageservice.security.model.User;
import com.epam.microserviceslearning.storageservice.security.model.UserRepository;
import com.epam.microserviceslearning.storageservice.security.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GithubAuthenticationProvider implements AuthenticationProvider {
    private static final String USER_ID_CLAIM = "user_id";

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final GithubAuthentication githubAuthentication = (GithubAuthentication) authentication;
        final String accessToken = githubAuthentication.getCredentials() != null
                ? githubAuthentication.getCredentials().toString()
                : null;

        if (accessToken != null) {
            final Map<String, Object> claims = tokenService.parseToken(accessToken);

            if (claims.containsKey(USER_ID_CLAIM)) {
                final long userId = (Integer) claims.get(USER_ID_CLAIM);
                final Optional<User> user = userRepository.findById(userId);

                if (user.isPresent()) {
                    githubAuthentication.setAuthorities(getAuthorities(user.get()));
                    githubAuthentication.setPrincipal(user.get());
                    githubAuthentication.setAuthenticated(true);
                }
            }
        } else {
            // TODO: it's just a hack for backward compatibility, need to implement proper authenticated access from other services
            // anonymous access
            githubAuthentication.setAuthorities(List.of(UserRole.USER));
            githubAuthentication.setAuthenticated(true);
        }

        return githubAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(GithubAuthentication.class);
    }

    private List<UserRole> getAuthorities(User user) {
        final UserRole role = user.getRole();
        final List<UserRole> authorities = new ArrayList<>();
        authorities.add(role);

        if (role == UserRole.ADMIN) {
            authorities.add(UserRole.USER);
        }

        return authorities;
    }
}
